package com.example.eshop.controller.dto;

import com.example.eshop.item.model.ItemEntity;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@NoArgsConstructor
public class DetailedItemDto {
    private long itemSeq;
    private String sellerId;
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

    public DetailedItemDto(ItemEntity item, String sellerId) {
        this.itemSeq = item.getItemNo();
        this.sellerId = sellerId;
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
}
