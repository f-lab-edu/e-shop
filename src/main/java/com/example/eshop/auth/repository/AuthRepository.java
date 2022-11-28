package com.example.eshop.auth.repository;

import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AuthRepository {
    TokenEntity findByTypeAndRandomStr(String type, String randomStr);
    boolean isDuplicatedId(String userId);
    void signin(UserEntity user);
    UserEntity findUserByUserId(String userId);
    UserEntity findUserByUserNo(long userNo);
}
