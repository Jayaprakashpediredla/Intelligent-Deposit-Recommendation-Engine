package com.nexuswealth.common.exception;

import com.nexuswealth.common.response.ResponseCode;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {

        super(ResponseCode.RESOURCE_NOT_FOUND, message);
    }

}
