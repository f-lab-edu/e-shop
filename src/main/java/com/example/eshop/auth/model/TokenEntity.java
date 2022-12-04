package com.example.eshop.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TokenEntity {
    private long tokenNo;

    private String userType;

    private long userNo;

    private String randomAccessToken;

    private LocalDateTime accessExpireDt;

    private String randomRefreshToken;

    private LocalDateTime refreshExpireDt;

    private LocalDateTime regDt;

    private LocalDateTime updDt;


    public TokenEntity(long userNo,
                       String randomAccessToken,
                       String randomRefreshToken,
                       long accessTokenExpiration,
                       long refreshTokenExpiration) {
        this.userType = "01";
        this.userNo = userNo;
        this.randomAccessToken = randomAccessToken;
        this.accessExpireDt = LocalDateTime.now().plusSeconds(accessTokenExpiration);
        this.randomRefreshToken = randomRefreshToken;
        this.refreshExpireDt = LocalDateTime.now().plusSeconds(refreshTokenExpiration);
    }
}
