package com.example.eshop.admin.member.core.service.impl;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.controller.dto.AdminUserDto;
import com.example.eshop.admin.member.core.repository.AdminMemberRepository;
import com.example.eshop.admin.member.core.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {
    private final AdminMemberRepository adminMemberRepository;

    @Override
    public boolean isDuplicatedId(String id) {
        log.info("isDuplicatedId ::: {}", id);
        return adminMemberRepository.isDuplicatedId(id);
    }

    @Override
    @Transactional
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

        adminMemberRepository.insertAdminUserEntity(adminUser);
    }

    @Override
    public AdminUserEntity getAdminUserByAdminId(String adminId) {
        AdminUserEntity user = adminMemberRepository.findAdminUserByAdminId(adminId);
        checkUserExist(user);
        return user;
    }

    @Override
    public AdminUserEntity getAdminUserByUserNo(long userNo) {
        AdminUserEntity user = adminMemberRepository.findAdminUserByUserNo(userNo);
        checkUserExist(user);
        return user;
    }


    private void checkUserExist(AdminUserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }
}
