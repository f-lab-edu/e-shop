package com.example.eshop.member.auth.model;

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
    private String randomAccessToken;

    @Setter
    private LocalDateTime accessExpireDt;

    @Setter
    private String randomRefreshToken;

    @Setter
    private LocalDateTime refreshExpireDt;

    private LocalDateTime regDt;

    private LocalDateTime updDt;


    public TokenEntity(long userNo,
                       String randomAccessToken,
                       String randomRefreshToken,
                       LocalDateTime accessTokenExpiration,
                       LocalDateTime refreshTokenExpiration) {
        this.userType = "01";
        this.userNo = userNo;
        this.randomAccessToken = randomAccessToken;
        this.accessExpireDt = accessTokenExpiration;
        this.randomRefreshToken = randomRefreshToken;
        this.refreshExpireDt = refreshTokenExpiration;
    }
}
