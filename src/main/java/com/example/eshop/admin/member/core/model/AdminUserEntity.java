package com.example.eshop.admin.member.core.model;

import com.example.eshop.common.model.DateInfoEntity;
import com.example.eshop.common.type.MemberStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class AdminUserEntity extends DateInfoEntity {
    private long adminNo;

    private String adminId;

    @Setter
    private String name;

    @Setter
    private String status;

    private String levelCd;

    @Setter
    private String password;

    @Setter
    private String tel;

    @Setter
    private String postNum;

    @Setter
    private String address;

    @Setter
    private LocalDate lastLoginDt;

    @Setter
    private LocalDate modifyPwDt;

    @Builder
    public AdminUserEntity(String adminId, String name, String levelCd,
                           String password, String tel, String postNum,
                           String address) {
        this.adminId = adminId;
        this.name = name;
        this.status = MemberStatus.NORMAL.getCode();
        this.levelCd = levelCd;
        this.password = password;
        this.tel = tel;
        this.postNum = postNum;
        this.address = address;
    }
}
