package com.example.eshop.auth.service.impl;

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
        UserEntity user = authRepository.findUserByUserId(loginDto.getId());

        validateUser(user);

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new UserNotFoundException();
        }
        return new TokenDto(
                jwtUtil.generate(user.getUserNo(), TokenType.ACCESS),
                jwtUtil.generate(user.getUserNo(), TokenType.REFRESH)
        );
    }

    @Override
    public TokenDto refreshToken(long userSeq) {
        log.info("refreshToken ::: {}", userSeq);
        UserEntity user = authRepository.findUserByUserNo(userSeq);

        validateUser(user);

        return new TokenDto(
                jwtUtil.generate(user.getUserNo(), TokenType.ACCESS)
        );
    }

    private void validateUser(UserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }
}
