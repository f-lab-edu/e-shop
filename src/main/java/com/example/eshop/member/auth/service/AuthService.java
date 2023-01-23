package com.example.eshop.member.auth.service;

import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.member.auth.model.TokenEntity;

public interface AuthService {
    TokenDto login(LoginDto loginDto);
    TokenDto refreshToken(long userSeq);
    TokenEntity getAccessToken(String randomToken);
    TokenEntity getRefreshToken(String randomToken);
}
