package com.example.eshop.auth.repository;

import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AuthRepository {
    boolean isDuplicatedId(String userId);
    void signin(UserEntity user);
    UserEntity findUserByUserId(String userId);
    TokenEntity findAccessTokenByUserNo(long userNo);
    TokenEntity findRefreshTokenByLinkedTokenNo(long tokenNo);
    TokenEntity findAccessTokenByRandomToken(String randomToken);
    TokenEntity findRefreshTokenByRandomToken(String randomToken);
    void insertAccessToken(TokenEntity token);
    void insertRefreshToken(TokenEntity token);
    UserEntity findUserByUserNo(long userNo);
    void updateExpireDt(long userNo);
}
