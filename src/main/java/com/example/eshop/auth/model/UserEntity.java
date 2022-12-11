package com.example.eshop.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserEntity {
    private int userNo;

    private String userId;

    @Setter
    private String name;

    private String joinCode;

    private String status;

    @Setter
    private String password;

    @Setter
    private String tel;

    @Setter
    private String postNum;

    @Setter
    private String address;

    @Setter
    private String notiYn;

    private LocalDate notiAgreeDt;

    private LocalDate lastLoginDt;

    private LocalDate modifyPwDt;

    private LocalDate regDt;

    private LocalDate updDt;


    @Builder
    public UserEntity(String userId, String name, String joinCode,
                      String password, String tel, String postNum,
                      String address, String notiYn) {
        this.userId = userId;
        this.name = name;
        this.joinCode = joinCode;
        this.status = "0";
        this.password = password;
        this.tel = tel;
        this.postNum = postNum;
        this.address = address;
        this.notiYn = notiYn;
    }
}
