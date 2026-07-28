package com.nexuswealth.common.exception;

import com.nexuswealth.common.response.ResponseCode;

public abstract class BusinessException extends RuntimeException {

    private final ResponseCode responseCode;

    protected BusinessException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
