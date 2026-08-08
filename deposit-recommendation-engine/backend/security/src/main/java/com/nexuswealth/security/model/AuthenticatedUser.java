package com.nexuswealth.security.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AuthenticatedUser {

    private UUID userId;

    private UUID customerId;

    private String username;

    private String role;
}