package com.example.eshop.auth.repository;

import com.example.eshop.auth.model.TokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AuthRepository {
    TokenEntity findAccessTokenByUserNo(long userNo);
    TokenEntity findAccessTokenByAdminNo(long adminNo);
    TokenEntity findAccessTokenByRandomToken(String randomToken);
    TokenEntity findRefreshTokenByRandomToken(String randomToken);
    void insertToken(TokenEntity token);
    void updateToken(TokenEntity token);
}
