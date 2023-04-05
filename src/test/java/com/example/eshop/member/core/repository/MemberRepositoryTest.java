package com.example.eshop.member.core.repository;

import com.example.eshop.member.core.model.BuyerUserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Test
    @DisplayName("isDuplicatedId :: 정상 케이스")
    void isDuplicatedId() {
        boolean isDuplicated = memberRepository.isDuplicatedId("hjkim");

        assertTrue(isDuplicated);
    }

    @Test
    @Transactional
    @DisplayName("signin :: 정상 케이스")
    void insertUserEntity() {
        BuyerUserEntity user = BuyerUserEntity.builder()
                .userId("hjkimtest")
                .name("test")
                .joinCode("01")
                .password("test")
                .tel("01012341234")
                .postNum("000001")
                .address("Seoul")
                .notiYn("Y")
                .build();

        memberRepository.insertUserEntity(user);

        boolean isDuplicated = memberRepository.isDuplicatedId("hjkimtest");

        assertTrue(isDuplicated);
    }

    @Test
    @DisplayName("findUserByUserId :: 정상 케이스")
    void findUserByUserId() {
        BuyerUserEntity user = memberRepository.findUserByUserId("hjkim");

        assertEquals("hjkim", user.getUserId());
        assertEquals(1, user.getUserNo());
    }

    @Test
    @DisplayName("findUserByUserNo :: 정상 케이스")
    void findUserByUserNo() {
        BuyerUserEntity user = memberRepository.findUserByUserNo(1);

        assertEquals("hjkim", user.getUserId());
        assertEquals(1, user.getUserNo());
    }
}