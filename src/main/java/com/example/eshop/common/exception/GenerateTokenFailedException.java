package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class GenerateTokenFailedException extends BaseException {

    private static final long serialVersionUID = -793618238420505519L;

    public GenerateTokenFailedException() {
        super(ResultType.SERVICE_UNAVAILABLE);
    }
}
