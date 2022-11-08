package com.example.eshop.auth.repository;

import com.example.eshop.auth.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthRepository {
    boolean isDuplicatedId(String userId);
    void signin(UserEntity user);
    UserEntity findUserByUserId(String userId);
    UserEntity findUserByUserNo(long userNo);
}
