package com.example.eshop.member.core.service.impl;

import com.example.eshop.common.exception.UserNotFoundException;
import com.example.eshop.controller.dto.UserDto;
import com.example.eshop.member.core.model.UserEntity;
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
    public void signin(UserDto userDto) {
        log.info("signin ::: {}", userDto);

        UserEntity user = UserEntity.builder()
                .userId(userDto.getId())
                .name(userDto.getName())
                .joinCode(userDto.getJoinCode())
                .password(userDto.getPassword())
                .tel(userDto.getContact())
                .postNum(userDto.getPostNumber())
                .address(userDto.getAddress())
                .notiYn(userDto.getNotiYn())
                .build();

        memberRepository.insertUserEntity(user);
    }

    @Override
    public UserEntity getUserByUserId(String userId) {
        UserEntity user = memberRepository.findUserByUserId(userId);
        checkUserExist(user);
        return user;
    }

    @Override
    public UserEntity getUserByUserNo(long userNo) {
        UserEntity user = memberRepository.findUserByUserNo(userNo);
        checkUserExist(user);
        return user;
    }


    private void checkUserExist(UserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }
}
