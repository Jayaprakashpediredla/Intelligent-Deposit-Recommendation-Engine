package com.nexuswealth.customer.service;

import com.nexuswealth.common.enums.Role;
import com.nexuswealth.customer.dto.auth.LoginRequest;
import com.nexuswealth.customer.dto.auth.LoginResponse;
import com.nexuswealth.customer.entity.UserAccount;
import com.nexuswealth.customer.repository.UserAccountRepository;
import com.nexuswealth.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    private UserAccount userAccount;

    @BeforeEach
    void setUp() {

        userAccount =
                UserAccount.builder()
                        .id(UUID.randomUUID())
                        .username("admin")
                        .passwordHash("hashed-password")
                        .role(Role.ADMIN)
                        .enabled(true)
                        .build();
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request =
                new LoginRequest(
                        "admin",
                        "Password@123"
                );

        when(userAccountRepository.findByUsername("admin"))
                .thenReturn(Optional.of(userAccount));

        when(jwtService.generateToken(
                "admin",
                "ADMIN"
        )).thenReturn("jwt-token");

        LoginResponse response =
                authenticationService.login(request);

        assertThat(response)
                .isNotNull();

        assertThat(response.accessToken())
                .isEqualTo("jwt-token");

        assertThat(response.tokenType())
                .isEqualTo("Bearer");

        verify(authenticationManager)
                .authenticate(
                        any(UsernamePasswordAuthenticationToken.class)
                );

        verify(jwtService)
                .generateToken(
                        "admin",
                        "ADMIN"
                );
    }

    @Test
    void shouldFailWhenAuthenticatedUserCannotBeFound() {

        LoginRequest request =
                new LoginRequest(
                        "unknown",
                        "Password@123"
                );

        when(userAccountRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                () -> authenticationService.login(request)
        );
    }
}