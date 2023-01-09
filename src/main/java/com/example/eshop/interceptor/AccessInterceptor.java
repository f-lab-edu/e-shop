package com.example.eshop.interceptor;

import com.example.eshop.member.auth.model.TokenEntity;
import com.example.eshop.common.exception.AccessTokenRequiredException;
import com.example.eshop.common.exception.TokenExpiredException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.member.auth.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class AccessInterceptor extends AuthInterceptor {

    public AccessInterceptor(JwtUtil jwtUtil,
                             AuthService authService) {
        super(jwtUtil, authService);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.loadTokenFromHeader(request, TokenType.ACCESS);
        return super.preHandle(request, response, handler);
    }

    @Override
    protected void checkTokenExist() {
        if (StringUtils.isEmpty(getToken())) {
            throw new AccessTokenRequiredException();
        }
    }

    @Override
    protected void checkTokenExpired() {
        String randomToken = jwtUtil.getRandomToken(getToken());

        TokenEntity token = authService.getAccessToken(randomToken);
        setTokenEntity(token);

        if (token.getAccessExpireDt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }
    }
}
