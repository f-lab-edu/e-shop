package com.example.eshop.member.auth.service;

import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;

public interface AdminAuthService {
    TokenDto login(LoginDto loginDto);
    TokenDto refreshToken(long userSeq);
}
