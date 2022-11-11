package com.example.eshop.auth.service.impl;

import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.controller.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {
    private AuthServiceImpl authService;

    @Autowired
    public void setAuthServiceImplTest(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @Test
    @DisplayName("isDuplicatedId :: 정상 케이스")
    void isDuplicatedId() {
        boolean isDuplicated = authService.isDuplicatedId("hjkim");

        assertTrue(isDuplicated);
    }

    @Test
    @Transactional
    @DisplayName("signin :: 정상 케이스")
    void signin() {
        UserDto user = new UserDto("hjkimtest", "test", "01", "test",
                "01012341234", "000001",
                "Seoul", "Y");

        authService.signin(user);

        boolean checkRegistered = authService.isDuplicatedId("hjkimtest");

        assertTrue(checkRegistered);
    }

    @Test
    @DisplayName("login :: 정상 케이스")
    void login() {
        LoginDto loginDto = new LoginDto("hjkim", "asdf");

        TokenDto tokenDto = authService.login(loginDto);

        assertInstanceOf(TokenDto.class, tokenDto);
        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
    }

    @Test
    @DisplayName("refreshToken :: 정상 케이스")
    void refreshToken() {
        TokenDto tokenDto = authService.refreshToken(1);

        assertInstanceOf(TokenDto.class, tokenDto);
        assertNotNull(tokenDto.getAccessToken());
    }
}