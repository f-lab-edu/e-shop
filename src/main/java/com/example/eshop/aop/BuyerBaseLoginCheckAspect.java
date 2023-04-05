package com.example.eshop.aop;

import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.service.AuthService;
import com.example.eshop.member.core.model.BuyerUserEntity;
import com.example.eshop.member.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class BuyerBaseLoginCheckAspect extends BaseLoginCheckAspect {
    private final AuthService authService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Around(value = "@annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint pjp, LoginCheck loginCheck) throws Throwable {
        String token = super.loadTokenFromHttpRequest(TokenType.ACCESS);

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
}
