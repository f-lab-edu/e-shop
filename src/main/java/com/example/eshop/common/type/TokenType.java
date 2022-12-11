package com.example.eshop.common.type;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS("Authorization"),
    REFRESH("X-Authorization");

    private final String headerName;

    TokenType(String headerName) {
        this.headerName = headerName;
    }
}
