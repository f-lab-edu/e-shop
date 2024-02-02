package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class FileCreationFailedException extends BaseException {
    private static final long serialVersionUID = -5912391649959255589L;

    public FileCreationFailedException() {
        super(ResultType.SERVICE_UNAVAILABLE);
    }
}
