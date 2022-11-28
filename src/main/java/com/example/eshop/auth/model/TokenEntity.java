package com.example.eshop.auth.model;

import com.example.eshop.common.type.TokenType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TokenEntity {
    private long tokenNo;

    private long userNo;

    private TokenType type;

    private String randomStr;

    private LocalDate expireDt;

    private LocalDate discardDt;
}
