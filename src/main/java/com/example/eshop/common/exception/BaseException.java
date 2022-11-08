package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 181401123593734030L;

    private final ResultType resultType;

    public BaseException(ResultType resultType) {
        super(resultType.getMessage());
        this.resultType = resultType;
    }

    public ResultType getResultType() {
        return this.resultType;
    }
}
