package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class TokenExpiredException extends BaseException {

    private static final long serialVersionUID = 132625415464079900L;

    public TokenExpiredException() {
        super(ResultType.EXPIRED_TOKEN);
    }
}
