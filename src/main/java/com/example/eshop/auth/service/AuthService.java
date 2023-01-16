package com.example.eshop.auth.service;

import com.example.eshop.controller.dto.BuyerUserDto;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;

public interface AuthService {
    boolean isDuplicatedId(String id);
    void signin(BuyerUserDto buyerUserDto);
    TokenDto login(LoginDto loginDto);
    TokenDto refreshToken(long userSeq);
}
