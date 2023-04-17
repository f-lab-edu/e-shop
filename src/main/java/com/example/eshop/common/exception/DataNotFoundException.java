package com.example.eshop.common.exception;

import com.example.eshop.common.type.ResultType;

public class DataNotFoundException extends BaseException {
    private static final long serialVersionUID = 2374886448666333972L;

    public DataNotFoundException() {
        super(ResultType.DATA_NOT_FOUND);
    }
}
