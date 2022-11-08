package com.example.eshop.interceptor;

import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RefreshInterceptor extends AuthInterceptor {

    public RefreshInterceptor(JwtUtil jwtUtil) {
        super(jwtUtil);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.loadTokenFromHeader(request, TokenType.REFRESH);

        return super.preHandle(request, response, handler);
    }
}
