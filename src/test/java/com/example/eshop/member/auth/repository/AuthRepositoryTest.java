package com.example.eshop.member.auth.repository;

import com.example.eshop.member.auth.model.TokenEntity;
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
    private static final long ACCESS_TOKEN_EXPIRATION = 1800;
    private static final long REFRESH_TOKEN_EXPIRATION = 604800;

    private static final String ACCESS_RANDOM_TOKEN = "accessrandomtokentest";
    private static final String REFRESH_RANDOM_TOKEN = "refreshrandomtokentest";

    private AuthRepository authRepository;
    TokenEntity token;

    @Autowired
    public void setAuthRepository(AuthRepository authRepository) {
        this.authRepository = authRepository;
        this.token = new TokenEntity(1,
                ACCESS_RANDOM_TOKEN,
                REFRESH_RANDOM_TOKEN,
                LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRATION),
                LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION));
    }

    @Test
    @DisplayName("findAccessTokenByUserNo :: 정상 케이스")
    void findAccessTokenByUserNo() {
        TokenEntity token = authRepository.findAccessTokenByUserNo(1);

        assertEquals(1, token.getUserNo());
    }

    @Test
    @Transactional
    @DisplayName("findAccessTokenByRandomToken :: 정상 케이스")
    void findAccessTokenByRandomToken() {

        authRepository.upsertToken(token);

        TokenEntity insertedToken = authRepository.findAccessTokenByRandomToken(ACCESS_RANDOM_TOKEN);

        assertEquals(1, insertedToken.getUserNo());
        assertEquals(ACCESS_RANDOM_TOKEN, insertedToken.getRandomAccessToken());
    }

    @Test
    @Transactional
    @DisplayName("findRefreshTokenByRandomToken :: 정상 케이스")
    void findRefreshTokenByRandomToken() {

        authRepository.upsertToken(token);

        TokenEntity insertedToken = authRepository.findRefreshTokenByRandomToken(REFRESH_RANDOM_TOKEN);

        assertEquals(1, insertedToken.getUserNo());
        assertEquals(REFRESH_RANDOM_TOKEN, insertedToken.getRandomRefreshToken());
    }

    @Test
    @Transactional
    @DisplayName("upsertToken :: 정상 케이스")
    void upsertToken() {
        authRepository.upsertToken(token);

        TokenEntity insertedToken = authRepository.findAccessTokenByRandomToken(ACCESS_RANDOM_TOKEN);

        assertEquals(ACCESS_RANDOM_TOKEN, insertedToken.getRandomAccessToken());
        assertEquals(1, insertedToken.getUserNo());
    }
}