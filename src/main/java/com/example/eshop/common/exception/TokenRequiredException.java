package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class TokenRequiredException extends BaseException {

    private static final long serialVersionUID = 274231280552891826L;

    public TokenRequiredException() {
        super(ResultType.REQUIRED_TOKEN);
    }
}
