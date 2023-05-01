package com.example.eshop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ItemModificationDto {
    private String name;
    private long remains;
    private long price;
    private String intro;
    private String bigImage;
    private String smallImage;
    private String content;
    private String adYn;
    private String mdRecommendYn;
    private String fastYn;
}
