package com.example.eshop.interceptor;

import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.repository.AuthRepository;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
@RequiredArgsConstructor
public abstract class AuthInterceptor implements HandlerInterceptor {

    private static final String USER_SEQ_ATTRIBUTE_KEY = "userSeq";

    protected final JwtUtil jwtUtil;
    protected final AuthRepository authRepository;

    @Setter
    private String token;

    @Setter
    private String requestUri;

    @Setter
    private String tokenTypeName;

    @Setter
    private TokenEntity tokenEntity;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        checkTokenExist();
        checkTokenExpired();
        setUserSeqToAttribute(request);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    protected void loadTokenFromHeader(HttpServletRequest request, TokenType type) {
        this.setToken(request.getHeader(type.getHeaderName()));
        this.setRequestUri(request.getRequestURI());
        this.setTokenTypeName(type.name());
    }

    abstract void checkTokenExist();
    abstract void checkTokenExpired();

    private void setUserSeqToAttribute(HttpServletRequest request) {
        request.setAttribute(USER_SEQ_ATTRIBUTE_KEY, this.tokenEntity.getUserNo());
    }
}
