package com.example.eshop.admin.member.core.service.impl;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.common.exception.AccessForbiddenException;
import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.common.type.MemberType;
import com.example.eshop.controller.dto.AdminUserDto;
import com.example.eshop.admin.member.core.repository.AdminMemberRepository;
import com.example.eshop.admin.member.core.service.AdminMemberService;
import com.example.eshop.common.type.MemberStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .levelCd(adminUserDto.getType().getCode())
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
        AdminUserEntity user = adminMemberRepository.findAdminUserByAdminNo(userNo);
        checkUserExist(user);
        return user;
    }

    @Override
    public List<AdminUserEntity> getAdminUserListByUserNo(List<Long> userNoList) {
        return adminMemberRepository.findAdminUserListByUserNoList(userNoList);
    }


    private void checkUserExist(AdminUserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }

        checkUserStatus(user);
    }

    private void checkUserStatus(AdminUserEntity user) {
        if (user.getStatus() == null || user.getLevelCd() == null) {
            throw new AccessForbiddenException();
        }

        if (!user.getStatus().equals(MemberStatus.NORMAL.getCode())) {
            throw new AccessForbiddenException();
        }

        if (user.getLevelCd().equals(MemberType.BUYER.getCode())) {
            throw new AccessForbiddenException();
        }
    }
}
