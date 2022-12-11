package com.example.eshop.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TokenEntity {
    private long tokenNo;

    private String userType;

    private long userNo;

    @Setter
    private Long linkedTokenNo;

    private String token;

    private LocalDateTime expireDt;

    private LocalDateTime regDt;

    private LocalDateTime updDt;


    public TokenEntity(String userType,
                       long userNo,
                       String token,
                       LocalDateTime expireDt) {
        this.userType = userType;
        this.userNo = userNo;
        this.token = token;
        this.expireDt = expireDt;
    }
}
