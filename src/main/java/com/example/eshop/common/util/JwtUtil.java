package com.example.eshop.common.util;

import com.example.eshop.common.type.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private static final String RANDOM_STRING_KEY = "randomToken";

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public String generateRandomString(TokenType type) {
        String uuid = UUID.randomUUID().toString();

        int length = TokenType.ACCESS.equals(type) ? 50 : 55;
        String randomString = RandomUtil.generateString(length-uuid.length()-1);
        return uuid + "-" + randomString;
    }

    public String generate(String randomString) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(RANDOM_STRING_KEY, randomString);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
    }

    public String getRandomToken(String token) {
        return getClaimsFromToken(token).get(RANDOM_STRING_KEY, String.class);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
