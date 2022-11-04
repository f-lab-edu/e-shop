package com.example.eshop.auth.repository;

import com.example.eshop.auth.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthRepository {
    void signin(UserEntity user);
}
