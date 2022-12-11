package com.example.eshop.auth.service;

import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.controller.dto.UserDto;

public interface AuthService {
    boolean isDuplicatedId(String id);
    void signin(UserDto userDto);
    TokenDto login(LoginDto loginDto);
    TokenDto refreshToken(long userSeq);
}
