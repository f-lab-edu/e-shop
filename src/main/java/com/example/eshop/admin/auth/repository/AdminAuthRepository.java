package com.example.eshop.admin.auth.repository;

import com.example.eshop.admin.auth.model.AdminUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminAuthRepository {
    boolean isDuplicatedId(String adminId);
    void signin(AdminUserEntity adminUser);
    AdminUserEntity findUserByAdminId(String adminId);
}
