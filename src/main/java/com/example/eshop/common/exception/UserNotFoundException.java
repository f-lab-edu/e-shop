package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class UserNotFoundException extends BaseException {

    private static final long serialVersionUID = -1811801035782110037L;

    public UserNotFoundException() {
        super(ResultType.UNKNOWN_USER);
    }

}
