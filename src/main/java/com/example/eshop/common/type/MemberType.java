package com.example.eshop.common.type;

import lombok.Getter;

@Getter
public enum MemberType {
    BUYER("01"),
    SELLER("02"),
    ADMIN("03");

    private final String code;

    MemberType(String code) {
        this.code = code;
    }
}
