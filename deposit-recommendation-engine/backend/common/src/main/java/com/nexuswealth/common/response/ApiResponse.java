package com.nexuswealth.common.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class    ApiResponse<T> {

    private final boolean success;
    private final ResponseCode code;

    private final String message;

    private final T data;

    private final List<ApiError> errors;

    @Builder.Default
    private final Instant timestamp = Instant.now();

    /**
     * Success response with data.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(ResponseCode.SUCCESS)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Success response without data.
     */
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(ResponseCode.SUCCESS)
                .message(message)
                .build();
    }

    /**
     * Created response.
     */
    public static <T> ApiResponse<T> created(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(ResponseCode.CREATED)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Failure response.
     */
    public static <T> ApiResponse<T> failure(
            ResponseCode code,
            String message,
            List<ApiError> errors) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(code)
                .message(message)
                .errors(errors)
                .build();
    }
}