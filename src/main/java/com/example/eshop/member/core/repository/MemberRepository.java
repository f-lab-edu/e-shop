package com.example.eshop.member.core.repository;

import com.example.eshop.member.core.model.BuyerUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberRepository {
    boolean isDuplicatedId(String userId);
    void insertUserEntity(BuyerUserEntity user);
    BuyerUserEntity findUserByUserId(String userId);
    BuyerUserEntity findUserByUserNo(long userNo);
}
