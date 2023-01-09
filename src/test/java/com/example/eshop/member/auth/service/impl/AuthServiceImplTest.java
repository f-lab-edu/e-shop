package com.example.eshop.member.auth.service.impl;

import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.member.auth.model.TokenEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {
    private AuthServiceImpl authService;
    private JwtUtil jwtUtil;
    LoginDto loginDto;

    @Autowired
    public void setAuthServiceImplTest(AuthServiceImpl authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.loginDto = new LoginDto("hjkim", "asdf");
    }

    @Test
    @Transactional
    @DisplayName("login :: 정상 케이스")
    void login() {
        TokenDto tokenDto = authService.login(loginDto);

        assertInstanceOf(TokenDto.class, tokenDto);
        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
    }

    @Test
    @Transactional
    @DisplayName("refreshToken :: 정상 케이스")
    void refreshToken() {
        TokenDto tokenDto = authService.refreshToken(1);

        assertInstanceOf(TokenDto.class, tokenDto);
        assertNotNull(tokenDto.getAccessToken());
    }

    @Test
    @Transactional
    @DisplayName("getAccessToken :: 정상 케이스")
    void getAccessToken() {
        TokenDto tokenDto = authService.login(loginDto);
        String randomToken = jwtUtil.getRandomToken(tokenDto.getAccessToken());

        TokenEntity token = authService.getAccessToken(randomToken);

        assertInstanceOf(TokenEntity.class, token);
        assertEquals(1, token.getUserNo());
    }

    @Test
    @Transactional
    @DisplayName("getRefreshToken :: 정상 케이스")
    void getRefreshToken() {
        TokenDto tokenDto = authService.login(loginDto);
        String randomToken = jwtUtil.getRandomToken(tokenDto.getRefreshToken());

        TokenEntity token = authService.getRefreshToken(randomToken);

        assertInstanceOf(TokenEntity.class, token);
        assertEquals(1, token.getUserNo());
    }
}