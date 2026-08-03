package com.nexuswealth.customer.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    private HttpServletRequest request;

    @BeforeEach
    void setUp() {

        handler = new GlobalExceptionHandler();

        request = mock(HttpServletRequest.class);

        when(request.getRequestURI())
                .thenReturn("/api/v1/customers/test");
    }

    @Test
    @DisplayName("Should return 404 when resource is not found")
    void shouldHandleResourceNotFoundException() {

        ResourceNotFoundException exception =
                new ResourceNotFoundException(
                        "Customer not found"
                );

        ResponseEntity<ErrorResponse> response =
                handler.handleResourceNotFoundException(
                        exception,
                        request
                );

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
                .isNotNull();

        assertThat(response.getBody().getStatus())
                .isEqualTo(404);

        assertThat(response.getBody().getError())
                .isEqualTo("Not Found");

        assertThat(response.getBody().getMessage())
                .isEqualTo("Customer not found");

        assertThat(response.getBody().getPath())
                .isEqualTo("/api/v1/customers/test");

        assertThat(response.getBody().getTimestamp())
                .isNotNull();
    }

    @Test
    @DisplayName("Should return 409 when duplicate resource exists")
    void shouldHandleDuplicateResourceException() {

        DuplicateResourceException exception =
                new DuplicateResourceException(
                        "Customer already exists"
                );

        ResponseEntity<ErrorResponse> response =
                handler.handleDuplicateResourceException(
                        exception,
                        request
                );

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CONFLICT);

        assertThat(response.getBody())
                .isNotNull();

        assertThat(response.getBody().getStatus())
                .isEqualTo(409);

        assertThat(response.getBody().getError())
                .isEqualTo("Conflict");

        assertThat(response.getBody().getMessage())
                .isEqualTo("Customer already exists");
    }

    @Test
    @DisplayName("Should return 500 for unexpected exception")
    void shouldHandleGenericException() {

        RuntimeException exception =
                new RuntimeException(
                        "Internal database information"
                );

        ResponseEntity<ErrorResponse> response =
                handler.handleGenericException(
                        exception,
                        request
                );

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        assertThat(response.getBody())
                .isNotNull();

        assertThat(response.getBody().getStatus())
                .isEqualTo(500);

        assertThat(response.getBody().getMessage())
                .isEqualTo("An unexpected error occurred");

        /*
         * Internal exception message must NOT be exposed.
         */
        assertThat(response.getBody().getMessage())
                .doesNotContain(
                        "Internal database information"
                );
    }
}