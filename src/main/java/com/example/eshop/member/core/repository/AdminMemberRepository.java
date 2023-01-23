package com.example.eshop.member.core.repository;

import com.example.eshop.member.core.model.AdminUserEntity;
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
