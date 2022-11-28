package com.example.eshop.common.util;

import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.repository.AuthRepository;
import com.example.eshop.common.type.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    private Key key;

    private final RandomUtil randomUtil;
    private final AuthRepository authRepository;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public String generate(TokenType type) {

        int length = TokenType.ACCESS.equals(type) ? 50 : 55;
        String randomString = randomUtil.generateString(length);

        Map<String, Object> claims = new HashMap<>();
        claims.put("randomId", randomString);

        final Date createdDate = new Date();

        long expirationTime = TokenType.ACCESS.equals(type) ?
                accessTokenExpiration : refreshTokenExpiration;
        final Date expirationDate = new Date(createdDate.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(claims)      // 1
                .setIssuedAt(createdDate)       // 2
                .setExpiration(expirationDate)      // 3
                .signWith(key)
                .compact();
    }


    public boolean isValid(String token) {
        Claims claims;
        try {
            claims = getClaimsFromToken(token);
        } catch (ExpiredJwtException ex) {
            return false;
        }

        String type = claims.get("type", String.class);
        String randomStr = claims.get("randomId", String.class);
        TokenEntity tokenEntity = authRepository.findByTypeAndRandomStr(type, randomStr);

        return tokenEntity.getExpireDt().isAfter(LocalDate.now())
                && tokenEntity.getDiscardDt().isAfter(LocalDate.now());
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
