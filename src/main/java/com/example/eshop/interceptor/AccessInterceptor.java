package com.example.eshop.interceptor;

import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.repository.AuthRepository;
import com.example.eshop.common.exception.AccessTokenRequiredException;
import com.example.eshop.common.exception.TokenExpiredException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class AccessInterceptor extends AuthInterceptor {

    public AccessInterceptor(JwtUtil jwtUtil,
                             AuthRepository authRepository) {
        super(jwtUtil, authRepository);
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

        TokenEntity token = authRepository.findAccessTokenByRandomToken(randomToken);
        setTokenEntity(token);

        if (token.getExpireDt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }
    }
}
