package com.example.eshop.controller.dto;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.item.model.ItemEntity;

import java.time.LocalDate;

public class SimpleItemDto {
    private long itemSeq;
    private String sellerId;
    private String sellerName;
    private String name;
    private String status;
    private long price;
    private long remains;
    private String fastYn;
    private String adYn;
    private String mdRcommendedYn;
    private long salesAmount;
    private LocalDate regDt;

    public SimpleItemDto(ItemEntity item, AdminUserEntity seller) {
        this.itemSeq = item.getItemNo();
        this.sellerId = seller.getAdminId();
        this.sellerName = seller.getName();
        this.name = item.getName();
        this.status = item.getStatus();
        this.price = item.getPrice();
        this.remains = item.getRemains();
        this.fastYn = item.getFastYn();
        this.adYn = item.getAdYn();
        this.mdRcommendedYn = item.getMdRecommendYn();
        this.salesAmount = item.getCellCnt();
        this.regDt = item.getRegDt();
    }
}
