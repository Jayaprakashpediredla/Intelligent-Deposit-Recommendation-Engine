package com.nexuswealth.common.exception;

import com.nexuswealth.common.response.ResponseCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ResourceNotFoundExceptionTest {

    @Test
    void shouldCreateResourceNotFoundException() {

        ResourceNotFoundException exception =
                new ResourceNotFoundException("Customer not found");

        assertThat(exception.getResponseCode())
                .isEqualTo(ResponseCode.RESOURCE_NOT_FOUND);

        assertThat(exception.getMessage())
                .isEqualTo("Customer not found");
    }

}
