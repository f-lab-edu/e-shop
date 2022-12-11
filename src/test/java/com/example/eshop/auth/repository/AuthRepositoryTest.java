package com.example.eshop.auth.repository;

import com.example.eshop.auth.model.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthRepositoryTest {
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
        System.out.println(user.getUserId());

        assertEquals("hjkim", user.getUserId());
        assertEquals(1, user.getUserNo());
    }

    @Test
    @DisplayName("findUserByUserNo :: 정상 케이스")
    void findUserByUserNo() {
        UserEntity user = authRepository.findUserByUserNo(1);

        assertEquals("hjkim", user.getUserId());
        assertEquals(1, user.getUserNo());
    }
}