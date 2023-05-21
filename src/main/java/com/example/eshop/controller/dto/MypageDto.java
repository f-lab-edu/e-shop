package com.example.eshop.controller.dto;

import com.example.eshop.member.core.model.BuyerUserEntity;
import lombok.ToString;

@ToString
public class MypageDto {
    private BuyerUserEntity buyer;

    public MypageDto(BuyerUserEntity buyer) {
        this.buyer = buyer;
    }

    public long getUserSeq() {
        return buyer.getUserNo();
    }

    public String getName() {
        return buyer.getName();
    }

    public String getConnect() {
        return buyer.getTel();
    }

    public String getEmail() {
        return buyer.getUserId();
    }
}
