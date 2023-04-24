package com.example.eshop.member.core.model;

import com.example.eshop.common.model.DateInfoEntity;
import com.example.eshop.common.type.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BuyerUserEntity extends DateInfoEntity {
    private long userNo;

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

    @Builder
    public BuyerUserEntity(String userId, String name, String joinCode,
                           String password, String tel, String postNum,
                           String address, String notiYn) {
        this.userId = userId;
        this.name = name;
        this.joinCode = joinCode;
        this.status = MemberStatus.NORMAL.getCode();
        this.password = password;
        this.tel = tel;
        this.postNum = postNum;
        this.address = address;
        this.notiYn = notiYn;
    }
}
