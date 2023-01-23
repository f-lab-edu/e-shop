package com.example.eshop.admin.member.service;

import com.example.eshop.admin.member.model.AdminUserEntity;
import com.example.eshop.controller.dto.AdminUserDto;

public interface AdminMemberService {
    boolean isDuplicatedId(String id);
    void signin(AdminUserDto adminUserDto);
    AdminUserEntity getAdminUserByAdminId(String adminId);
    AdminUserEntity getAdminUserByUserNo(long userNo);
}
