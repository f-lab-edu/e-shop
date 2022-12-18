package com.example.eshop.auth.repository;

import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.model.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthRepositoryTest {
    private static final long accessTokenExpiration = 1800;
    private static final long refreshTokenExpiration = 604800;

    private AuthRepository authRepository;

    @Autowired
    public void setAuthRepository(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Test
    @DisplayName("isDuplicatedId :: 정상 케이스")
    void isDuplicatedId() {
        boolean isDuplicated = authRepository.isDuplicatedId("hjkim");

        assertTrue(isDuplicated);
    }

    @Test
    @Transactional
    @DisplayName("signin :: 정상 케이스")
    void signin() {
        UserEntity user = UserEntity.builder()
                .userId("hjkimtest")
                .name("test")
                .joinCode("01")
                .password("test")
                .tel("01012341234")
                .postNum("000001")
                .address("Seoul")
                .notiYn("Y")
                .build();

        authRepository.signin(user);

        boolean isDuplicated = authRepository.isDuplicatedId("hjkimtest");

        assertTrue(isDuplicated);
    }

    @Test
    @DisplayName("findUserByUserId :: 정상 케이스")
    void findUserByUserId() {
        UserEntity user = authRepository.findUserByUserId("hjkim");

        assertEquals("hjkim", user.getUserId());
        assertEquals(1, user.getUserNo());
    }

    @Test
    @DisplayName("findAccessTokenByUserNo :: 정상 케이스")
    void findAccessTokenByUserNo() {
        TokenEntity token = authRepository.findAccessTokenByUserNo(1);

        assertEquals(1, token.getUserNo());
    }

    @Test
    @DisplayName("findAccessTokenByRandomToken :: 정상 케이스")
    void findAccessTokenByRandomToken() {

        // TODO : insertToken

        TokenEntity token = authRepository.findAccessTokenByRandomToken("");

        assertEquals(1, token.getUserNo());
    }

    @Test
    @DisplayName("findRefreshTokenByRandomToken :: 정상 케이스")
    void findRefreshTokenByRandomToken() {

        // TODO : insertToken

        TokenEntity token = authRepository.findRefreshTokenByRandomToken("");

        assertEquals(1, token.getUserNo());
    }

    @Test
    @Transactional
    @DisplayName("insertToken :: 정상 케이스")
    void insertToken() {
        String accessRandomToken = "accessrandomtokentest";
        String refreshRandomToken = "refreshrandomtokentest";

        TokenEntity token = new TokenEntity(1,
                accessRandomToken,
                refreshRandomToken,
                LocalDateTime.now().plusSeconds(accessTokenExpiration),
                LocalDateTime.now().plusSeconds(refreshTokenExpiration));

        authRepository.insertToken(token);

        TokenEntity insertedToken = authRepository.findAccessTokenByRandomToken(accessRandomToken);
        assertEquals(accessRandomToken, insertedToken.getRandomAccessToken());
        assertEquals(1, insertedToken.getUserNo());
    }
}