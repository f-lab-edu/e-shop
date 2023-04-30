package com.example.eshop.controller.dto;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.item.model.ItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class DetailedItemDto {
    private ItemEntity item;
    private AdminUserEntity seller;

    // TODO : categoryName

    public DetailedItemDto(ItemEntity item
            , AdminUserEntity seller) {
        this.item = item;
        this.seller = seller;
    }

    public long getItemSeq() {
        return item.getItemNo();
    }

    public long getSellerSeq() {
        return seller.getAdminNo();
    }

    public String getSellerId() {
        return seller.getAdminId();
    }

    public long getCategorySeq() {
        return item.getCategoryNo();
    }

    public String getName() {
        return item.getName();
    }

    public String getFastYn() {
        return item.getFastYn();
    }

    public String getSmallIamge() {
        return item.getSmallImage();
    }

    public String getBigImage() {
        return item.getBigImage();
    }

    public long getPrice() {
        return item.getPrice();
    }

    public String getIntro() {
        return item.getIntro();
    }

    public String getContent() {
        return item.getContent();
    }

    public long getRemains() {
        return item.getRemains();
    }

    public String getStatus() {
        return item.getStatus();
    }

    public String getAdYn() {
        return item.getAdYn();
    }

    public String getMdRecommendYn() {
        return item.getMdRecommendYn();
    }

    public long getCellCount() {
        return item.getCellCnt();
    }

    public long getLikes() {
        return item.getLikes();
    }

    public LocalDateTime getRegDate() {
        return item.getRegDt();
    }

    public LocalDateTime getUpdDate() {
        return item.getUpdDt();
    }
}
