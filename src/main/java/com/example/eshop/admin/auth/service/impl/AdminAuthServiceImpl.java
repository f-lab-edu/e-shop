package com.example.eshop.admin.auth.service.impl;

import com.example.eshop.admin.auth.model.AdminUserEntity;
import com.example.eshop.admin.auth.repository.AdminAuthRepository;
import com.example.eshop.admin.auth.service.AdminAuthService;
import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.controller.dto.AdminUserDto;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminAuthRepository adminAuthRepository;

    @Override
    public boolean isDuplicatedId(String id) {
        log.info("isDuplicatedId ::: {}", id);
        return adminAuthRepository.isDuplicatedId(id);
    }

    @Override
    public void signin(AdminUserDto adminUserDto) {
        log.info("signin ::: {}", adminUserDto);

        AdminUserEntity adminUser = AdminUserEntity.builder()
                .adminId(adminUserDto.getId())
                .name(adminUserDto.getName())
                .levelCd(adminUserDto.getLevelCode())
                .password(adminUserDto.getPassword())
                .tel(adminUserDto.getContact())
                .postNum(adminUserDto.getPostNumber())
                .address(adminUserDto.getAddress())
                .build();

        adminAuthRepository.signin(adminUser);
    }

    @Override
    @Transactional
    public TokenDto login(LoginDto loginDto) {
        log.info("login ::: {}", loginDto);

        AdminUserEntity user = getValidatedUserEntity(loginDto);

//        // 기존 토큰 있는지 확인
//        TokenEntity token = authRepository.findAccessTokenByUserNo(user.getAdminNo());
//        if (isValid(token)) {
//            return getJwtTokenFromRandomToken(token);
//        }
//
//        TokenEntity newToken = generateNewTokenEntity(user.getUserNo());
//        authRepository.insertToken(newToken);
//
//        return getJwtTokenFromRandomToken(newToken);
        return new TokenDto();
    }

    private AdminUserEntity getValidatedUserEntity(LoginDto loginDto) {
        AdminUserEntity user = adminAuthRepository.findUserByAdminId(loginDto.getId());
        checkUserExist(user);
        checkPasswordEqual(user.getPassword(), loginDto.getPassword());

        return user;
    }

    private void checkUserExist(AdminUserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }

    private void checkPasswordEqual(String rawPassword, String requestPassword) {
        if (!rawPassword.equals(requestPassword)) {
            throw new UserNotFoundException();
        }
    }

    private boolean isValid(TokenEntity tokenEntity) {
        return tokenEntity != null &&
                tokenEntity.getAccessExpireDt().isAfter(LocalDateTime.now());
    }
}
