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
    TokenEntity findAccessTokenByRandomToken(String randomToken);
    TokenEntity findRefreshTokenByRandomToken(String randomToken);
    void insertToken(TokenEntity token);
    void updateExpireDt(long userNo);
}
