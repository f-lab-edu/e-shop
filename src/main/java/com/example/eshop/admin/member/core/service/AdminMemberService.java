package com.example.eshop.admin.member.core.service;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.controller.dto.AdminUserDto;

import java.util.List;

public interface AdminMemberService {
    boolean isDuplicatedId(String id);
    void signin(AdminUserDto adminUserDto);
    AdminUserEntity getAdminUserByAdminId(String adminId);
    AdminUserEntity getAdminUserByUserNo(long userNo);
    List<AdminUserEntity> getAdminUserListByUserNo(List<Long> userNoList);
}
