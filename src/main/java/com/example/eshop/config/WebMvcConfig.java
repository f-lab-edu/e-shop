package com.example.eshop.config;

import com.example.eshop.interceptor.AccessInterceptor;
import com.example.eshop.interceptor.RefreshInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AccessInterceptor accessInterceptor;
    private final RefreshInterceptor refreshInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor)
                .excludePathPatterns("/v1/auth/check/duplicated-id",
                        "/v1/auth/signin", "/v1/auth/login", "/v1/auth/token/refresh",
                        "/v1/mypage/info", "/v1/admin/**");
        registry.addInterceptor(refreshInterceptor)
                .addPathPatterns("/v1/auth/token/refresh");
    }
}
