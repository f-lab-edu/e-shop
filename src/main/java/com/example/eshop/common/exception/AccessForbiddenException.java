package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class AccessForbiddenException extends BaseException {

    public AccessForbiddenException() {
        super(ResultType.NO_ROLE);
    }
}
