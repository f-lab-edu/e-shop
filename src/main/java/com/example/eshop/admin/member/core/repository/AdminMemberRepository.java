package com.example.eshop.admin.member.core.repository;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMemberRepository {
    boolean isDuplicatedId(String userId);
    void insertAdminUserEntity(AdminUserEntity user);
    AdminUserEntity findAdminUserByAdminId(String adminId);
    AdminUserEntity findAdminUserByAdminNo(long adminNo);
    List<AdminUserEntity> findAdminUserListByUserNoList(List<Long> userNoList);
}
