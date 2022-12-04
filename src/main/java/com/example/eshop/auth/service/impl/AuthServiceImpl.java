package com.example.eshop.auth.service.impl;

import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.model.UserEntity;
import com.example.eshop.auth.repository.AuthRepository;
import com.example.eshop.auth.service.AuthService;
import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.controller.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;

    @Override
    public boolean isDuplicatedId(String id) {
        log.info("isDuplicatedId ::: {}", id);
        return authRepository.isDuplicatedId(id);
    }

    @Override
    public void signin(UserDto userDto) {
        log.info("signin ::: {}", userDto);

        UserEntity user = UserEntity.builder()
                .userId(userDto.getId())
                .name(userDto.getName())
                .joinCode(userDto.getJoinCode())
                .password(userDto.getPassword())
                .tel(userDto.getContact())
                .postNum(userDto.getPostNumber())
                .address(userDto.getAddress())
                .notiYn(userDto.getNotiYn())
                .build();

        authRepository.signin(user);
    }

    @Override
    public TokenDto login(LoginDto loginDto) {
        log.info("login ::: {}", loginDto);

        // 비밀번호 맞는지 확인
        UserEntity user = authRepository.findUserByUserId(loginDto.getId());
        checkUserExist(user);

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new UserNotFoundException();
        }

        // 토큰 있는지 확인
        TokenEntity token = authRepository.findAccessTokenByUserNo(user.getUserNo());

        if (token != null) {
            // 있다면 유효기간 지났는지 확인
            if (token.getAccessExpireDt().isAfter(LocalDateTime.now())) {
                return new TokenDto(
                        jwtUtil.generate(token.getRandomAccessToken()),
                        jwtUtil.generate(token.getRandomRefreshToken())
                );
            }
        }

        // 토큰 없거나 유효기간 지났으면 : 토큰 생성
        TokenEntity newToken = new TokenEntity(user.getUserNo(),
                jwtUtil.generateRandomString(TokenType.ACCESS),
                jwtUtil.generateRandomString(TokenType.REFRESH));

        // db insert
        authRepository.insertToken(newToken);

        return new TokenDto(
                jwtUtil.generate(newToken.getRandomAccessToken()),
                jwtUtil.generate(newToken.getRandomRefreshToken())
        );
    }

    @Override
    public TokenDto refreshToken(long userSeq) {
        log.info("refreshToken ::: {}", userSeq);

        // 기존토큰 만료일 update
        authRepository.updateExpireDt(userSeq);


        // 토큰 생성
        TokenEntity newToken = new TokenEntity(userSeq,
                jwtUtil.generateRandomString(TokenType.ACCESS),
                jwtUtil.generateRandomString(TokenType.REFRESH));

        // db insert
        authRepository.insertToken(newToken);

        return new TokenDto(
                jwtUtil.generate(newToken.getRandomAccessToken()),
                jwtUtil.generate(newToken.getRandomRefreshToken())
        );
    }

    private void checkUserExist(UserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }
}
