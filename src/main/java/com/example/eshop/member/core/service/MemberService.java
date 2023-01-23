package com.example.eshop.member.core.service;

import com.example.eshop.controller.dto.BuyerUserDto;
import com.example.eshop.member.core.model.BuyerUserEntity;

public interface MemberService {
    boolean isDuplicatedId(String id);
    void signin(BuyerUserDto buyerUserDto);
    BuyerUserEntity getUserByUserId(String userId);
    BuyerUserEntity getUserByUserNo(long userNo);
}
