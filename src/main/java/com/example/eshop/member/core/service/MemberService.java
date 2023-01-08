package com.example.eshop.member.core.service;

import com.example.eshop.controller.dto.UserDto;
import com.example.eshop.member.core.model.UserEntity;

public interface MemberService {
    boolean isDuplicatedId(String id);
    void signin(UserDto userDto);
    UserEntity getUserByUserId(String userId);
}
