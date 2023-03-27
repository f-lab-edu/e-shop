package com.example.eshop.aop.admin;

import com.example.eshop.aop.BaseLoginCheckAspect;
import com.example.eshop.auth.model.TokenEntity;
import com.example.eshop.auth.service.AuthService;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.admin.member.core.service.AdminMemberService;
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
public class RefreshTokenCheckAspect extends BaseLoginCheckAspect {
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final AdminMemberService memberService;

    @Around(value = "@annotation(refreshTokenCheck)")
    public Object loginCheck(ProceedingJoinPoint pjp, RefreshTokenCheck refreshTokenCheck) throws Throwable {
        String token = super.loadTokenFromHttpRequest(TokenType.REFRESH);

        String randomToken = jwtUtil.getRandomToken(token);

        TokenEntity tokenEntity = authService.getRefreshToken(randomToken);

        AdminUserEntity user = memberService.getAdminUserByUserNo(tokenEntity.getUserNo());

        Method method = MethodSignature.class.cast(pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            for (Annotation parameterAnnotation : parameterAnnotations[i]) {
                if (parameterAnnotation.annotationType() == Admin.class && method.getParameterTypes()[i] == AdminUserEntity.class) {
                    args[i] = user;
                }
            }
        }

        return pjp.proceed(args);
    }
}
