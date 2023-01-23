package com.example.eshop.member.auth.service.impl;

import com.example.eshop.common.exception.GenerateTokenFailedException;
import com.example.eshop.common.exception.InvalidTokenException;
import com.example.eshop.common.exception.TokenExpiredException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.member.auth.model.AdminTokenEntity;
import com.example.eshop.member.core.model.AdminUserEntity;
import com.example.eshop.member.auth.repository.AdminAuthRepository;
import com.example.eshop.member.auth.service.AdminAuthService;
import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.member.core.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final JwtUtil jwtUtil;
    private final AdminMemberService adminMemberService;
    private final AdminAuthRepository adminAuthRepository;

    private static final long accessTokenExpiration = 1800;
    private static final long refreshTokenExpiration = 604800;

    @Override
    @Transactional
    public TokenDto login(LoginDto loginDto) {
        log.info("login ::: {}", loginDto);

        AdminUserEntity user = getValidatedUserEntity(loginDto);

        AdminTokenEntity token = adminAuthRepository.findAccessTokenByUserNo(user.getAdminNo());
        if (isValid(token)) {
            return getJwtTokenFromRandomToken(token);
        }

        AdminTokenEntity newToken = generateNewAdminTokenEntity(user.getAdminNo());
        adminAuthRepository.insertAdminToken(newToken);

        return getJwtTokenFromRandomToken(newToken);
    }

    @Override
    @Transactional
    public TokenDto refreshToken(long adminNo) {
        log.info("refreshToken ::: {}", adminNo);

        AdminTokenEntity token = adminAuthRepository.findAccessTokenByUserNo(adminNo);
        AdminTokenEntity newToken = generateNewAdminTokenEntity(adminNo);

        updateToken(token, newToken);

        return getJwtTokenFromRandomToken(newToken);
    }

    @Override
    public AdminTokenEntity getAccessToken(String randomToken) {
        log.info("getAccessToken ::: {}", randomToken);

        AdminTokenEntity token = adminAuthRepository.findAccessTokenByRandomToken(randomToken);
        validateAccessTokenEntity(token);

        return token;
    }

    @Override
    public AdminTokenEntity getRefreshToken(String randomToken) {
        log.info("getRefreshToken ::: {}", randomToken);

        AdminTokenEntity token = adminAuthRepository.findRefreshTokenByRandomToken(randomToken);
        validateRefreshTokenEntity(token);

        return token;
    }


    private AdminUserEntity getValidatedUserEntity(LoginDto loginDto) {
        AdminUserEntity user = adminMemberService.getAdminUserByAdminId(loginDto.getId());
        checkPasswordEqual(user.getPassword(), loginDto.getPassword());

        return user;
    }

    private void checkPasswordEqual(String rawPassword, String requestPassword) {
        if (!rawPassword.equals(requestPassword)) {
            throw new UserNotFoundException();
        }
    }

    private boolean isValid(AdminTokenEntity token) {
        return token != null &&
                token.getAccessExpireDt().isAfter(LocalDateTime.now());
    }

    private TokenDto getJwtTokenFromRandomToken(AdminTokenEntity token) {
        return new TokenDto(
                jwtUtil.generate(token.getRandomAccessToken()),
                jwtUtil.generate(token.getRandomRefreshToken())
        );
    }

    private AdminTokenEntity generateNewAdminTokenEntity(long adminNo) {
        String accessRandomToken = generateUniqueAccessRandomToken();
        String refreshRandomToken = generateUniqueRefreshRandomToken();

        LocalDateTime accessTokenExpTime = LocalDateTime.now().plusSeconds(accessTokenExpiration);
        LocalDateTime refreshTokenExpTime = LocalDateTime.now().plusSeconds(refreshTokenExpiration);

        if (accessRandomToken.isEmpty() || refreshRandomToken.isEmpty()) {
            throw new GenerateTokenFailedException();
        }

        return new AdminTokenEntity(adminNo,
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

    private void updateToken(AdminTokenEntity token, AdminTokenEntity newToken) {
        token.setRandomAccessToken(newToken.getRandomAccessToken());
        token.setAccessExpireDt(newToken.getAccessExpireDt());
        token.setRandomRefreshToken(newToken.getRandomRefreshToken());
        token.setRefreshExpireDt(newToken.getRefreshExpireDt());

        adminAuthRepository.updateToken(token);
    }

    private void validateAccessTokenEntity(AdminTokenEntity adminTokenEntity) {
        if (adminTokenEntity == null) {
            throw new InvalidTokenException();
        }

        if (adminTokenEntity.getAccessExpireDt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }
    }

    private void validateRefreshTokenEntity(AdminTokenEntity adminTokenEntity) {
        if (adminTokenEntity == null) {
            throw new InvalidTokenException();
        }

        if (adminTokenEntity.getRefreshExpireDt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }
    }
}
