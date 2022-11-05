package com.example.eshop.advice;

import com.example.eshop.common.dto.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body
                                  , MethodParameter returnType
                                  , MediaType selectedContentType
                                  , Class<? extends HttpMessageConverter<?>> selectedConverterType
                                  , ServerHttpRequest request
                                  , ServerHttpResponse response) {
        Result result;

        if (body instanceof Result) {
            result = (Result) body;
        } else {
            result = new Result(body);
        }

        response.setStatusCode(result.getStatus());
        return result;
    }
}
