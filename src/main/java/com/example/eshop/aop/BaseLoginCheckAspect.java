package com.example.eshop.aop;

import com.example.eshop.common.exception.AccessTokenRequiredException;
import com.example.eshop.common.type.TokenType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseLoginCheckAspect {
    protected String loadTokenFromHttpRequest(TokenType tokenType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(tokenType.getHeaderName());

        if (StringUtils.isEmpty(token)) {
            throw new AccessTokenRequiredException();
        }
        return token;
    }
}
