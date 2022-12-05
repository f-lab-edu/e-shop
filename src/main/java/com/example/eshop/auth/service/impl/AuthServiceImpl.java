package com.example.eshop.auth.service.impl;

import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.model.UserEntity;
import com.example.eshop.auth.repository.AuthRepository;
import com.example.eshop.auth.service.AuthService;
import com.example.eshop.common.exception.GenerateTokenFailedException;
import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.controller.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

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

        UserEntity user = getValidatedUserEntity(loginDto);

        // 기존 토큰 있는지 확인
        TokenEntity token = authRepository.findAccessTokenByUserNo(user.getUserNo());
        if (isValid(token)) {
            return getJwtTokenFromRandomToken(token);
        }

        TokenEntity newToken = generateNewTokenEntity(user.getUserNo());
        authRepository.insertToken(newToken);

        return getJwtTokenFromRandomToken(newToken);
    }

    @Override
    @Transactional
    public TokenDto refreshToken(long userSeq) {
        log.info("refreshToken ::: {}", userSeq);

        authRepository.updateExpireDt(userSeq);

        TokenEntity newToken = generateNewTokenEntity(userSeq);
        authRepository.insertToken(newToken);

        return getJwtTokenFromRandomToken(newToken);
    }

    private UserEntity getValidatedUserEntity(LoginDto loginDto) {
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
        return tokenEntity != null &&
                tokenEntity.getAccessExpireDt().isAfter(LocalDateTime.now());
    }

    private TokenEntity generateNewTokenEntity(long userNo) {
        String accessRandomToken = generateUniqueAccessRandomToken();
        String refreshRandomToken = generateUniqueRefreshRandomToken();

        if (accessRandomToken.isEmpty() || refreshRandomToken.isEmpty()) {
            throw new GenerateTokenFailedException();
        }

        return new TokenEntity(userNo,
                accessRandomToken,
                refreshRandomToken,
                accessTokenExpiration,
                refreshTokenExpiration);
    }

    private String generateUniqueAccessRandomToken() {
        String accessRandomToken = jwtUtil.generateRandomString(TokenType.ACCESS);
        TokenEntity accessTokenEntity = authRepository.findAccessTokenByRandomToken(accessRandomToken);

        int count = 0;
        while (accessTokenEntity != null || count > 10) {
            accessRandomToken = jwtUtil.generateRandomString(TokenType.ACCESS);
            accessTokenEntity = authRepository.findAccessTokenByRandomToken(accessRandomToken);
            count += 1;
        }

        return accessTokenEntity != null ? "" : accessRandomToken;
    }

    private String generateUniqueRefreshRandomToken() {
        String refreshRandomToken = jwtUtil.generateRandomString(TokenType.REFRESH);
        TokenEntity refreshTokenEntity = authRepository.findRefreshTokenByRandomToken(refreshRandomToken);

        int count = 0;
        while (refreshTokenEntity != null || count > 10) {
            refreshRandomToken = jwtUtil.generateRandomString(TokenType.REFRESH);
            refreshTokenEntity = authRepository.findRefreshTokenByRandomToken(refreshRandomToken);
            count += 1;
        }

        return refreshTokenEntity != null ? "" : refreshRandomToken;
    }

    private TokenDto getJwtTokenFromRandomToken(TokenEntity token) {
        return new TokenDto(
                jwtUtil.generate(token.getRandomAccessToken()),
                jwtUtil.generate(token.getRandomRefreshToken())
        );
    }
}
