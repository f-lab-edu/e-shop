package com.example.eshop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BuyerUserDto {
    private String id;
    private String name;
    private String joinCode;
    private String password;
    private String contact;
    private String postNumber;
    private String address;
    private String notiYn;
}
