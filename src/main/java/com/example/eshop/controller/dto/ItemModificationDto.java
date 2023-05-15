package com.example.eshop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
@AllArgsConstructor
public class ItemModificationDto {
    private String name;
    private long remains;
    private long price;
    private String intro;
    private String content;
    private String adYn;
    private String mdRecommendYn;
    private String fastYn;
}
