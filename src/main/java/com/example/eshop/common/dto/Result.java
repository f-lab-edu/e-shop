package com.example.eshop.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    @JsonIgnore
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

    public String getCode() {
        return this.resultType.getCode();
    }

    public String getMessage() {
        return this.resultType.getMessage();
    }
}
