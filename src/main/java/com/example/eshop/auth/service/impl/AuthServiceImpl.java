package com.example.eshop.auth.service.impl;

import com.example.eshop.auth.model.UserEntity;
import com.example.eshop.auth.repository.AuthRepository;
import com.example.eshop.auth.service.AuthService;
import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.controller.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;

    @Override
    public boolean isDuplicatedId(String id) {
        return authRepository.isDuplicatedId(id);
    }

    @Override
    public void signin(UserDto userDto) {
        UserEntity user = UserEntity.builder()
                .userId(userDto.getId())
                .name(userDto.getName())
                .joinCode(userDto.getJoinCode())
                .password(userDto.getPassword())
                .tel(userDto.getContact())
                .postNum(userDto.getPostNumber())
                .address(userDto.getAddress())
                .notiYn(userDto.getNotiYn())
                .build();

        authRepository.signin(user);
    }

    @Override
    public TokenDto login(LoginDto loginDto) {
        UserEntity user = authRepository.findUserByUserId(loginDto.getId());

        validateUser(user);

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new UserNotFoundException();
        }
        return new TokenDto(
                jwtUtil.generate(user.getUserNo(), TokenType.ACCESS),
                jwtUtil.generate(user.getUserNo(), TokenType.REFRESH)
        );
    }

    @Override
    public TokenDto refreshToken(long userSeq) {
        UserEntity user = authRepository.findUserByUserNo(userSeq);

        validateUser(user);

        return new TokenDto(
                jwtUtil.generate(user.getUserNo(), TokenType.ACCESS)
        );
    }

    private void validateUser(UserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }
}
