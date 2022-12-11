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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;

    private static final long accessTokenExpiration = 1800;
    private static final long refreshTokenExpiration = 604800;

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
    @Transactional
    public TokenDto login(LoginDto loginDto) {
        log.info("login ::: {}", loginDto);

        UserEntity user = getAuthenticatedUserEntity(loginDto);

        // 기존 토큰 있는지 확인
        TokenEntity accessToken = authRepository.findAccessTokenByUserNo(user.getUserNo());
        if (isValid(accessToken)) {
            TokenEntity refreshToken = authRepository
                    .findRefreshTokenByLinkedTokenNo(accessToken.getTokenNo());
            return getJwtTokenFromRandomToken(accessToken, refreshToken);
        }

        TokenEntity newAccessToken = generateNewTokenEntity(user.getUserNo(), TokenType.ACCESS);
        authRepository.insertAccessToken(newAccessToken);

        TokenEntity newRefreshToken = generateNewTokenEntity(user.getUserNo(), TokenType.REFRESH);
        newRefreshToken.setLinkedTokenNo(newAccessToken.getTokenNo());
        authRepository.insertRefreshToken(newRefreshToken);

        return getJwtTokenFromRandomToken(newAccessToken, newRefreshToken);
    }

    @Override
    @Transactional
    public TokenDto refreshToken(long userSeq) {
        log.info("refreshToken ::: {}", userSeq);

        authRepository.updateExpireDt(userSeq);

        TokenEntity accessToken = generateNewTokenEntity(userSeq, TokenType.ACCESS);
        authRepository.insertAccessToken(accessToken);

        TokenEntity refreshToken = generateNewTokenEntity(userSeq, TokenType.REFRESH);
        refreshToken.setLinkedTokenNo(accessToken.getTokenNo());
        authRepository.insertRefreshToken(refreshToken);

        return getJwtTokenFromRandomToken(accessToken, refreshToken);
    }

    private UserEntity getAuthenticatedUserEntity(LoginDto loginDto) {
        UserEntity user = authRepository.findUserByUserId(loginDto.getId());
        checkUserExist(user);
        checkPasswordEqual(user.getPassword(), loginDto.getPassword());

        return user;
    }

    private void checkUserExist(UserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }

    private void checkPasswordEqual(String rawPassword, String requestPassword) {
        if (!rawPassword.equals(requestPassword)) {
            throw new UserNotFoundException();
        }
    }

    private boolean isValid(TokenEntity tokenEntity) {
        return tokenEntity != null;
    }

    private TokenEntity generateNewTokenEntity(long userNo, TokenType type) {
        String randomToken = jwtUtil.generateRandomString(type);

        long expPeriod = TokenType.ACCESS.equals(type) ? accessTokenExpiration : refreshTokenExpiration;
        LocalDateTime expTime = LocalDateTime.now().plusSeconds(expPeriod);

        return new TokenEntity("01",
                userNo,
                randomToken,
                expTime);
    }

    private TokenDto getJwtTokenFromRandomToken(TokenEntity accessToken,
                                                TokenEntity refreshToken) {
        return new TokenDto(
                jwtUtil.generate(accessToken.getToken()),
                jwtUtil.generate(refreshToken.getToken())
        );
    }
}
