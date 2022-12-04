package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class AccessTokenRequiredException extends BaseException {

    private static final long serialVersionUID = 274231280552891826L;

    public AccessTokenRequiredException() {
        super(ResultType.ACCESS_TOKEN_REQUIRED);
    }
}
