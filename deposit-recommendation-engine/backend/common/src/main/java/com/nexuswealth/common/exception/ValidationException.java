package com.nexuswealth.common.exception;

import com.nexuswealth.common.response.ResponseCode;

public class ValidationException extends BusinessException{
    public ValidationException(String message) {
        super(ResponseCode.VALIDATION_FAILED, message);
    }
}
