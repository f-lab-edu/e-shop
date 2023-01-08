package com.example.eshop.member.auth.service.impl;

import com.example.eshop.member.auth.model.TokenEntity;
import com.example.eshop.member.core.model.UserEntity;
import com.example.eshop.member.auth.repository.AuthRepository;
import com.example.eshop.member.auth.service.AuthService;
import com.example.eshop.common.exception.GenerateTokenFailedException;
import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.member.core.service.MemberService;
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
    private final MemberService memberService;
    private final AuthRepository authRepository;

    private static final long accessTokenExpiration = 1800;
    private static final long refreshTokenExpiration = 604800;

    @Override
    @Transactional
    public TokenDto login(LoginDto loginDto) {
        log.info("login ::: {}", loginDto);

        UserEntity user = getValidatedUserEntity(loginDto);

        TokenEntity token = authRepository.findAccessTokenByUserNo(user.getUserNo());
        if (isValid(token)) {
            return getJwtTokenFromRandomToken(token);
        }

        TokenEntity newToken = generateNewTokenEntity(user.getUserNo());
        authRepository.upsertToken(newToken);

        return getJwtTokenFromRandomToken(newToken);
    }

    @Override
    @Transactional
    public TokenDto refreshToken(long userSeq) {
        log.info("refreshToken ::: {}", userSeq);

        TokenEntity newToken = generateNewTokenEntity(userSeq);
        authRepository.upsertToken(newToken);

        return getJwtTokenFromRandomToken(newToken);
    }

    private UserEntity getValidatedUserEntity(LoginDto loginDto) {
        UserEntity user = memberService.getUserByUserId(loginDto.getId());
        checkPasswordEqual(user.getPassword(), loginDto.getPassword());
        return user;
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

        LocalDateTime accessTokenExpTime = LocalDateTime.now().plusSeconds(accessTokenExpiration);
        LocalDateTime refreshTokenExpTime = LocalDateTime.now().plusSeconds(refreshTokenExpiration);

        if (accessRandomToken.isEmpty() || refreshRandomToken.isEmpty()) {
            throw new GenerateTokenFailedException();
        }

        return new TokenEntity(userNo,
                accessRandomToken,
                refreshRandomToken,
                accessTokenExpTime,
                refreshTokenExpTime);
    }

    private String generateUniqueAccessRandomToken() {
        return jwtUtil.generateRandomString(TokenType.ACCESS);
    }

    private String generateUniqueRefreshRandomToken() {
        return jwtUtil.generateRandomString(TokenType.REFRESH);
    }

    private TokenDto getJwtTokenFromRandomToken(TokenEntity token) {
        return new TokenDto(
                jwtUtil.generate(token.getRandomAccessToken()),
                jwtUtil.generate(token.getRandomRefreshToken())
        );
    }
}
