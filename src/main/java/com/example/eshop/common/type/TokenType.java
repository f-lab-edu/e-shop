package com.example.eshop.common.type;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS(1, "Authorization"),
    REFRESH(2, "X-Authorization");

    private final int expMultiplier;
    private final String headerName;

    TokenType(int expMultiplier, String headerName) {
        this.expMultiplier = expMultiplier;
        this.headerName = headerName;
    }
}
