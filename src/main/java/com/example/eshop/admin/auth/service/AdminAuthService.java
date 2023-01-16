package com.example.eshop.admin.auth.service;

import com.example.eshop.controller.dto.AdminUserDto;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;

public interface AdminAuthService {
    boolean isDuplicatedId(String id);
    void signin(AdminUserDto adminUserDto);
    TokenDto login(LoginDto loginDto);
}
