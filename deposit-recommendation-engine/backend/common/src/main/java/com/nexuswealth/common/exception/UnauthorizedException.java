package com.nexuswealth.common.exception;

import com.nexuswealth.common.response.ResponseCode;

public class UnauthorizedException extends BusinessException{
    public UnauthorizedException(String message) {
        super(ResponseCode.UNAUTHORIZED, message);
    }
}
