package com.example.eshop.controller.dto;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@NoArgsConstructor
public class DetailedItemDto {
    private long itemSeq;
    private String userId;
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
    private LocalDate regDate;
}
