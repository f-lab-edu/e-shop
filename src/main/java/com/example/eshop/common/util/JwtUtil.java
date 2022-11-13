package com.example.eshop.common.util;

import com.example.eshop.common.type.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generate(long userSeq, TokenType type) {

        // 1. token 내부에 저장할 정보
        Map<String, Object> claims = new HashMap<>();
        claims.put("userSeq", userSeq);
        claims.put("type", type.name());

        // 2. token 생성일
        final Date createdDate = new Date();

        // 3. token 만료일
        long expirationTime = TokenType.ACCESS.equals(type) ? accessTokenExpiration
                : refreshTokenExpiration;

        final Date expirationDate = new Date(createdDate.getTime() + expirationTime);


        return Jwts.builder()
                .setClaims(claims)      // 1
                .setIssuedAt(createdDate)       // 2
                .setExpiration(expirationDate)      // 3
                .signWith(key)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(String token) {
        try {
            final Date expiration = getClaimsFromToken(token).getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

    public String getTypeFromToken(String token) {
        return getClaimsFromToken(token).get("type", String.class);
    }


    public Long getUserSeqFromToken(String token) {
        return getClaimsFromToken(token).get("userSeq", Long.class);
    }

    private JwtUtil() { }

}
