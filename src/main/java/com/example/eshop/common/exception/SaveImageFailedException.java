package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class SaveImageFailedException extends BaseException {
    private static final long serialVersionUID = -4718981620407475901L;

    public SaveImageFailedException() {
        super(ResultType.SERVICE_UNAVAILABLE);
    }
}
