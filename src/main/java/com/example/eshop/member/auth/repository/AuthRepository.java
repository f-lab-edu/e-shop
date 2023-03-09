package com.example.eshop.member.auth.repository;

import com.example.eshop.member.auth.model.TokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AuthRepository {
    TokenEntity findAccessTokenByUserNo(long userNo);
    TokenEntity findAccessTokenByRandomToken(String randomToken);
    TokenEntity findRefreshTokenByRandomToken(String randomToken);
    void insertToken(TokenEntity token);
    void updateToken(TokenEntity token);
}
