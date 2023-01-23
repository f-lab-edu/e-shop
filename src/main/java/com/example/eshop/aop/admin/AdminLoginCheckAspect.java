package com.example.eshop.aop.admin;

import com.example.eshop.common.exception.AccessTokenRequiredException;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.common.util.JwtUtil;
import com.example.eshop.member.auth.model.AdminTokenEntity;
import com.example.eshop.member.auth.service.AdminAuthService;
import com.example.eshop.member.core.model.AdminUserEntity;
import com.example.eshop.member.core.service.AdminMemberService;
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
public class AdminLoginCheckAspect {
    private final AdminAuthService authService;
    private final AdminMemberService memberService;
    private final JwtUtil jwtUtil;

    @Around(value = "@annotation(adminLoginCheck)")
    public Object loginCheck(ProceedingJoinPoint pjp, AdminLoginCheck adminLoginCheck) throws Throwable {
        String token = loadTokenFromHttpRequest();

        String randomToken = jwtUtil.getRandomToken(token);

        AdminTokenEntity adminToken = authService.getAccessToken(randomToken);

        AdminUserEntity user = memberService.getAdminUserByUserNo(adminToken.getUserNo());

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



    private String loadTokenFromHttpRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(TokenType.ACCESS.getHeaderName());

        if (StringUtils.isEmpty(token)) {
            throw new AccessTokenRequiredException();
        }
        return token;
    }
}
