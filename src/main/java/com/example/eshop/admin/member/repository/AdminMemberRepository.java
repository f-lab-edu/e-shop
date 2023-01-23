package com.example.eshop.admin.member.repository;

import com.example.eshop.admin.member.model.AdminUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMemberRepository {
    boolean isDuplicatedId(String userId);
    void insertAdminUserEntity(AdminUserEntity user);
    AdminUserEntity findAdminUserByAdminId(String adminId);
    AdminUserEntity findAdminUserByUserNo(long adminNo);
}
