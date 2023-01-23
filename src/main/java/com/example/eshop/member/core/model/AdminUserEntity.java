package com.example.eshop.member.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AdminUserEntity {
    private long adminNo;

    private String adminId;

    private String name;

    private String status;

    private String levelCd;

    private String password;

    private String tel;

    private String postNum;

    private String address;

    private LocalDate lastLoginDt;

    private LocalDate modifyPwDt;

    private LocalDate regDt;

    private LocalDate updDt;

    @Builder
    public AdminUserEntity(String adminId, String name, String levelCd,
                           String password, String tel, String postNum,
                           String address) {
        this.adminId = adminId;
        this.name = name;
        this.status = "0";
        this.levelCd = levelCd;
        this.password = password;
        this.tel = tel;
        this.postNum = postNum;
        this.address = address;
    }
}
