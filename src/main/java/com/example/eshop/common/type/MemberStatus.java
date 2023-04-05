package com.example.eshop.common.type;

import lombok.Getter;

@Getter
public enum MemberStatus {
    NORMAL("0"),
    SUSPENDED("1"),
    WITHDRAWAL("2");

    private final String code;

    MemberStatus(String code) {
        this.code = code;
    }
}
