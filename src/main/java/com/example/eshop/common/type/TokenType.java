package com.example.eshop.common.type;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS(1),
    REFRESH(2);

    private final int expMultiplier;

    TokenType(int expMultiplier) {
        this.expMultiplier = expMultiplier;
    }
}
