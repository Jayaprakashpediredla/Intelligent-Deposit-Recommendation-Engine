package com.nexuswealth.customer.dto.auth;

public record LoginResponse(

        String accessToken,

        String tokenType,

        long expiresIn

) {
}