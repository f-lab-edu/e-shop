package com.example.eshop.admin.member.auth.service;

import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.admin.member.auth.model.AdminTokenEntity;

public interface AdminAuthService {
    TokenDto login(LoginDto loginDto);
    TokenDto refreshToken(long adminNo);
    AdminTokenEntity getAccessToken(String randomToken);
    AdminTokenEntity getRefreshToken(String randomToken);
}
