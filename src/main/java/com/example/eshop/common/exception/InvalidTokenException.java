package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class InvalidTokenException extends BaseException {

    private static final long serialVersionUID = -6068418816730522621L;

    public InvalidTokenException() {
        super(ResultType.INVALID_TOKEN);
    }
}
