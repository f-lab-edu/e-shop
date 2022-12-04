package com.example.eshop.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TokenEntity {
    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;


    private long tokenNo;

    private String userType;

    private long userNo;

    private String randomAccessToken;

    private LocalDateTime accessExpireDt;

    private String randomRefreshToken;

    private LocalDateTime refreshExpireDt;

    private LocalDateTime regDt;

    private LocalDateTime updDt;


    @Builder
    public TokenEntity(long userNo,
                       String randomAccessToken,
                       String randomRefreshToken) {
        this.userType = "01";
        this.userNo = userNo;
        this.randomAccessToken = randomAccessToken;
        this.accessExpireDt = LocalDateTime.now().plusSeconds(accessTokenExpiration);
        this.randomRefreshToken = randomRefreshToken;
        this.refreshExpireDt = LocalDateTime.now().plusSeconds(refreshTokenExpiration);
    }
}
