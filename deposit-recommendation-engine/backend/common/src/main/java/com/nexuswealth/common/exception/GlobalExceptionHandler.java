package com.nexuswealth.common.exception;

import com.nexuswealth.common.response.ApiResponse;
import com.nexuswealth.common.response.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException exception) {

        ApiResponse<Void> response =
                ApiResponse.failure(
                        exception.getResponseCode(),
                        exception.getMessage(),
                        null);

        return ResponseEntity
                .status(exception.getResponseCode().getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnknownException(
            Exception exception) {

        ApiResponse<Void> response =
                ApiResponse.failure(
                        ResponseCode.INTERNAL_SERVER_ERROR,
                        "Unexpected server error",
                        null);

        return ResponseEntity
                .status(ResponseCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(response);
    }
}