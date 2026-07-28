package com.nexuswealth.common.exception;

import com.nexuswealth.common.response.ResponseCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValidationExceptionTest {

    @Test
    void shouldCreateValidationException() {
        ValidationException exception = new ValidationException("Invalid input");

        assertThat(exception.getResponseCode()).isEqualTo(ResponseCode.VALIDATION_FAILED);
        assertThat(exception.getMessage()).isEqualTo("Invalid input");
    }

}
