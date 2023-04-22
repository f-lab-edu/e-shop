package com.example.eshop.controller.dto;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.item.model.ItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class DetailedItemDto {
    private long itemSeq;
    private long sellerSeq;
    private String sellerId;
    private long categorySeq;
    private String categoryName;
    private String name;
    private String fastYn;
    private String smallImage;
    private String bigImage;
    private long price;
    private String intro;
    private String content;
    private long remains;
    private String status;
    private String adYn;
    private String mdRecommendYn;
    private long cellCount;
    private long likes;
    private LocalDate regDate;
    private LocalDate updDate;

    public DetailedItemDto(ItemEntity item, AdminUserEntity seller) {
        this.itemSeq = item.getItemNo();
        this.sellerSeq = seller.getAdminNo();
        this.sellerId = seller.getAdminId();
        this.categorySeq = item.getCategoryNo();
        this.name = item.getName();
        this.fastYn = item.getFastYn();
        this.smallImage = item.getSmallImage();
        this.bigImage = item.getBigImage();
        this.price = item.getPrice();
        this.intro = item.getIntro();
        this.content = item.getContent();
        this.remains = item.getRemains();
        this.status = item.getStatus();
        this.adYn = item.getAdYn();
        this.mdRecommendYn = item.getMdRecommendYn();
        this.cellCount = item.getCellCnt();
        this.likes = item.getLikes();
        this.regDate = item.getRegDt();
        this.updDate = item.getUpdDt();
    }

    public DetailedItemDto(long categorySeq,
                           String name,
                           String fastYn,
                           String smallImage,
                           String bigImage,
                           long price,
                           String intro,
                           String content,
                           long remains,
                           String status,
                           String adYn,
                           String mdRecommendYn) {
        this.categorySeq = categorySeq;
        this.name = name;
        this.fastYn = fastYn;
        this.smallImage = smallImage;
        this.bigImage = bigImage;
        this.price = price;
        this.intro = intro;
        this.content = content;
        this.remains = remains;
        this.status = status;
        this.adYn = adYn;
        this.mdRecommendYn = mdRecommendYn;
    }
}
