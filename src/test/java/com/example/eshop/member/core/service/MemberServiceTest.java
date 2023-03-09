package com.example.eshop.member.core.service;

import com.example.eshop.controller.dto.BuyerUserDto;
import com.example.eshop.member.core.model.BuyerUserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {
    private MemberService memberService;

    @Autowired
    public void setMemberServiceTest(MemberService memberService) {
        this.memberService = memberService;
    }

    @Test
    @DisplayName("isDuplicatedId :: 정상 케이스")
    void isDuplicatedId() {
        boolean isDuplicated = memberService.isDuplicatedId("hjkim");

        assertTrue(isDuplicated);
    }

    @Test
    @Transactional
    @DisplayName("signin :: 정상 케이스")
    void signin() {
        BuyerUserDto user = new BuyerUserDto("hjkimtest", "test", "01", "test",
                "01012341234", "000001",
                "Seoul", "Y");

        memberService.signin(user);

        boolean checkRegistered = memberService.isDuplicatedId("hjkimtest");

        assertTrue(checkRegistered);
    }

    @Test
    @DisplayName("getUserByUserId :: 로그인 아이디로 유저정보 조회")
    void getUserByUserId() {
        BuyerUserEntity selected = memberService.getUserByUserId("hjkim");

        assertInstanceOf(BuyerUserEntity.class, selected);
        assertEquals(1, selected.getUserNo());
        assertEquals("hjkim", selected.getUserId());
    }

    @Test
    @DisplayName("getUserByUserNo :: 유저번호로 유저정보 조회")
    void getUserByUserNo() {
        BuyerUserEntity selected = memberService.getUserByUserNo(1);

        assertInstanceOf(BuyerUserEntity.class, selected);
        assertEquals(1, selected.getUserNo());
        assertEquals("hjkim", selected.getUserId());
    }
}
