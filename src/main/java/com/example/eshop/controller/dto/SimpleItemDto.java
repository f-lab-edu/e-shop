package com.example.eshop.controller.dto;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.item.model.ItemEntity;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class SimpleItemDto {

    private ItemEntity item;
    private AdminUserEntity seller;

    public SimpleItemDto(ItemEntity item, AdminUserEntity seller) {
        this.item = item;
        this.seller = seller;
    }

    public long getItemSeq() {
        return item.getItemNo();
    }

    public String getSellerId() {
        return seller.getAdminId();
    }

    public String getSellerName() {
        return seller.getName();
    }

    public String getStatus() {
        return item.getStatus();
    }

    public long getPrice() {
        return item.getPrice();
    }

    public long getRemain() {
        return item.getRemains();
    }

    public String getFastYn() {
        return item.getFastYn();
    }

    public String getAdYn() {
        return item.getAdYn();
    }

    public String getMdRecommendedYn() {
        return item.getMdRecommendYn();
    }

    public long getSalesAmount() {
        return item.getCellCnt();
    }

    public LocalDateTime getRegDt() {
        return item.getRegDt();
    }
}
