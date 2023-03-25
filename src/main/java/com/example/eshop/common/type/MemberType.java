package com.example.eshop.common.type;

import lombok.Getter;

@Getter
public enum MemberType {
    BUYER("0"),
    SELLER("1"),
    ADMIN("2");

    private final String code;

    MemberType(String code) {
        this.code = code;
    }
}
