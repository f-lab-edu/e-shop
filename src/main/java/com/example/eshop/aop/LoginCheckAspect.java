package com.example.eshop.aop;

import com.example.eshop.common.exception.AccessTokenRequiredException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.member.auth.model.TokenEntity;
import com.example.eshop.member.auth.service.AuthService;
import com.example.eshop.member.core.model.BuyerUserEntity;
import com.example.eshop.member.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {
    private final AuthService authService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Around(value = "@annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint pjp, LoginCheck loginCheck) throws Throwable {
        String token = loadTokenFromHttpRequest();

        String randomToken = jwtUtil.getRandomToken(token);

        TokenEntity tokenEntity = authService.getAccessToken(randomToken);

        BuyerUserEntity user = memberService.getUserByUserNo(tokenEntity.getUserNo());

        Method method = MethodSignature.class.cast(pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            for (Annotation parameterAnnotation : parameterAnnotations[i]) {
                if (parameterAnnotation.annotationType() == User.class && method.getParameterTypes()[i] == BuyerUserEntity.class) {
                    args[i] = user;
                }
            }
        }

        return pjp.proceed(args);
    }



    private String loadTokenFromHttpRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(TokenType.ACCESS.getHeaderName());

        if (StringUtils.isEmpty(token)) {
            throw new AccessTokenRequiredException();
        }
        return token;
    }
}
