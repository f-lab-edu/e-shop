package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class RefreshTokenRequiredException extends BaseException {

    private static final long serialVersionUID = -5585523792161344930L;

    public RefreshTokenRequiredException() {
        super(ResultType.REFRESH_TOKEN_REQUIRED);
    }
}
