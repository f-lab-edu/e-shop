package com.example.eshop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreationDto {
    private Long categorySeq;
    private String name;
    private String smallImage;
    private String bigImage;
    private long price;
    private String intro;
    private String content;
    private long remains;
    private String fastYn;
}
