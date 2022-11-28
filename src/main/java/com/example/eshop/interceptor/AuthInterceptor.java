package com.example.eshop.interceptor;

import com.example.eshop.common.exception.TokenExpiredException;
import com.example.eshop.common.exception.TokenRequiredException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
@RequiredArgsConstructor
public abstract class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    private static final String USER_SEQ_ATTRIBUTE_KEY = "userSeq";

    @Setter
    private String token;

    @Setter
    private String requestUri;

    @Setter
    private String tokenTypeName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        checkTokenExist();
        validateToken();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    protected void loadTokenFromHeader(HttpServletRequest request, TokenType type) {
        this.setToken(request.getHeader(type.getHeaderName()));
        this.setRequestUri(request.getRequestURI());
        this.setTokenTypeName(type.name());
    }

    private void checkTokenExist() {
        if (StringUtils.isEmpty(this.token)) {
            throw new TokenRequiredException();
        }
    }

    protected void validateToken() {
        if (!jwtUtil.isValid(this.token)) {
            throw new TokenExpiredException();
        }
    }

}
