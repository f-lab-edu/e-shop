package com.example.eshop.auth.service;

import com.example.eshop.controller.dto.UserDto;

public interface AuthService {
    boolean isDuplicatedId(String id);
    void signin(UserDto userDto);
}
