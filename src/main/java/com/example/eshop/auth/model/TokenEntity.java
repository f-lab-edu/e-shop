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
    private long groupNo;

    private String type;

    private String token;

    private LocalDateTime expireDt;

    private LocalDateTime regDt;

    private LocalDateTime updDt;


    public TokenEntity(String userType,
                       long userNo,
                       String type,
                       String token,
                       LocalDateTime expireDt) {
        this.userType = userType;
        this.userNo = userNo;
        this.type = type;
        this.token = token;
        this.expireDt = expireDt;
    }
}
