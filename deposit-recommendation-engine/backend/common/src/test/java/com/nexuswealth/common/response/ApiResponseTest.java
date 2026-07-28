/*
package com.nexuswealth.common.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseTest {

    @Test
    @DisplayName("Should create success response with data")
    void shouldCreateSuccessResponseWithData() {

        String data = "Customer Created";
        ApiResponse<String> response =
                ApiResponse.success(data, "Operation Successful");

        assertThat(response).isNotNull();

        assertThat(response.isSuccess()).isTrue();

        assertThat(response.getCode())
                .isEqualTo(ResponseCode.SUCCESS);

        assertThat(response.getMessage())
                .isEqualTo("Operation Successful");

        assertThat(response.getData())
                .isEqualTo(data);

        assertThat(response.getErrors())
                .isNull();

        assertThat(response.getTimestamp())
                .isNotNull();

    }

    @Test
    @DisplayName("Should create success response without data")
    void shouldCreateSuccessWithoutData() {

        ApiResponse<Void> response =
                ApiResponse.success("Customer Updated");

        assertThat(response.isSuccess()).isTrue();

        assertThat(response.getCode())
                .isEqualTo(ResponseCode.SUCCESS);

        assertThat(response.getData())
                .isNull();

        assertThat(response.getErrors())
                .isNull();

    }

    @Test
    @DisplayName("Should create created response")
    void shouldCreateCreatedResponse() {

        ApiResponse<Integer> response =
                ApiResponse.created(1001, "Customer Created");

        assertThat(response.isSuccess()).isTrue();

        assertThat(response.getCode())
                .isEqualTo(ResponseCode.CREATED);

        assertThat(response.getData())
                .isEqualTo(1001);

    }

    @Test
    @DisplayName("Should create failure response")
    void shouldCreateFailureResponse() {

        ApiError error =
                ApiError.builder()
                        .field("salary")
                        .message("Salary cannot be negative")
                        .build();

        ApiResponse<Void> response =
                ApiResponse.failure(
                        ResponseCode.VALIDATION_FAILED,
                        "Validation Failed",
                        List.of(error));

        assertThat(response.isSuccess()).isFalse();

        assertThat(response.getCode())
                .isEqualTo(ResponseCode.VALIDATION_FAILED);

        assertThat(response.getErrors())
                .hasSize(1);

        assertThat(response.getErrors().get(0).getField())
                .isEqualTo("salary");

    }

}*/
