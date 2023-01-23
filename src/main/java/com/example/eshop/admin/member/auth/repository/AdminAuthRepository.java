package com.example.eshop.admin.member.auth.repository;

import com.example.eshop.admin.member.auth.model.AdminTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminAuthRepository {
    AdminTokenEntity findAccessTokenByUserNo(long adminNo);
    AdminTokenEntity findAccessTokenByRandomToken(String randomToken);
    AdminTokenEntity findRefreshTokenByRandomToken(String randomToken);
    void insertAdminToken(AdminTokenEntity token);
    void updateToken(AdminTokenEntity token);
}
