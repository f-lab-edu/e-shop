package com.example.eshop.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private ResultType resultType;

    private String code;
    private String message;
    private Object data;

    public Result(ResultType resultType) {
        this.resultType = resultType;
    }

    public Result(Object data) {
        this.resultType = ResultType.OK;
        this.data = data;
    }
}
