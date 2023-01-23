package com.example.eshop.member.core.service.impl;

import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.controller.dto.BuyerUserDto;
import com.example.eshop.member.core.model.BuyerUserEntity;
import com.example.eshop.member.core.repository.MemberRepository;
import com.example.eshop.member.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public boolean isDuplicatedId(String id) {
        log.info("isDuplicatedId ::: {}", id);
        return memberRepository.isDuplicatedId(id);
    }

    @Override
    @Transactional
    public void signin(BuyerUserDto buyerUserDto) {
        log.info("signin ::: {}", buyerUserDto);

        BuyerUserEntity user = BuyerUserEntity.builder()
                .userId(buyerUserDto.getId())
                .name(buyerUserDto.getName())
                .joinCode(buyerUserDto.getJoinCode())
                .password(buyerUserDto.getPassword())
                .tel(buyerUserDto.getContact())
                .postNum(buyerUserDto.getPostNumber())
                .address(buyerUserDto.getAddress())
                .notiYn(buyerUserDto.getNotiYn())
                .build();

        memberRepository.insertUserEntity(user);
    }

    @Override
    public BuyerUserEntity getUserByUserId(String userId) {
        BuyerUserEntity user = memberRepository.findUserByUserId(userId);
        checkUserExist(user);
        return user;
    }

    @Override
    public BuyerUserEntity getUserByUserNo(long userNo) {
        BuyerUserEntity user = memberRepository.findUserByUserNo(userNo);
        checkUserExist(user);
        return user;
    }


    private void checkUserExist(BuyerUserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }
}
