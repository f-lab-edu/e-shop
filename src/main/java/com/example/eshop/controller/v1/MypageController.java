package com.example.eshop.controller.v1;

import com.example.eshop.aop.LoginCheck;
import com.example.eshop.aop.User;
import com.example.eshop.controller.dto.MypageDto;
import com.example.eshop.member.core.model.BuyerUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/v1/mypage")
@RequiredArgsConstructor
public class MypageController {

    /**
     * 내 정보 조회
     *
     * @author hjkim
     * @param user
     */
    @LoginCheck
    @GetMapping("/info")
    public MypageDto getUser(@User BuyerUserEntity user) {
        log.info("getUser ::: {}", user);

        return new MypageDto(user);
    }
}