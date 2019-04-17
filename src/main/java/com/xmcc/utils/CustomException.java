package com.xmcc.utils;

import com.xmcc.common.ResultEnums;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private Integer code;

    public CustomException() {
        super();
    }

    public CustomException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message) {
        this(ResultEnums.FAIL.getCode(),message);
    }

}
