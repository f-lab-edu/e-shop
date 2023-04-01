package com.example.eshop.auth.service;

import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.auth.model.TokenEntity;

public interface AuthService {
    TokenDto login(LoginDto loginDto);
    TokenDto adminLogin(LoginDto loginDto);
    void logout(long userSeq);
    TokenDto refreshToken(long userSeq, String userType);
    TokenEntity getAccessToken(String randomToken);
    TokenEntity getRefreshToken(String randomToken);
}
