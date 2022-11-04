package com.example.eshop.auth.service;

import com.example.eshop.controller.dto.UserDto;

public interface AuthService {
    void signin(UserDto userDto);
}
