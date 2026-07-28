package com.nexuswealth.common.response;

import org.springframework.http.HttpStatus;

public enum ResponseCode {

    SUCCESS(HttpStatus.OK),

    CREATED(HttpStatus.CREATED),

    VALIDATION_FAILED(HttpStatus.BAD_REQUEST),

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),

    FORBIDDEN(HttpStatus.FORBIDDEN),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus httpStatus;

    ResponseCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}