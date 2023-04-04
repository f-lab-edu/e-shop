package com.example.eshop.controller.dto;

import com.example.eshop.common.type.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDto {
    private String id;
    private String name;
    private MemberType type;
    private String password;
    private String contact;
    private String postNumber;
    private String address;
}
