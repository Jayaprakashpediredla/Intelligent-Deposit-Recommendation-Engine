package com.nexuswealth.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private JwtService jwtService;

    private static final String SECRET =
            Base64.getEncoder().encodeToString(
                    "this-is-a-very-secure-secret-key-for-jwt"
                            .getBytes()
            );

    @BeforeEach
    void setUp() {

        jwtService =
                new JwtService(
                        SECRET,
                        3600000L
                );
    }

    @Test
    void shouldGenerateValidToken() {

        String token =
                jwtService.generateToken(
                        "admin",
                        "ADMIN"
                );

        assertThat(token)
                .isNotBlank();
    }

    @Test
    void shouldExtractUsername() {

        String token =
                jwtService.generateToken(
                        "admin",
                        "ADMIN"
                );

        String username =
                jwtService.extractUsername(token);

        assertThat(username)
                .isEqualTo("admin");
    }

    @Test
    void shouldExtractRole() {

        String token =
                jwtService.generateToken(
                        "admin",
                        "ADMIN"
                );

        String role =
                jwtService.extractRole(token);

        assertThat(role)
                .isEqualTo("ADMIN");
    }

    @Test
    void shouldValidateToken() {

        String token =
                jwtService.generateToken(
                        "admin",
                        "ADMIN"
                );

        boolean valid =
                jwtService.isTokenValid(
                        token,
                        "admin"
                );

        assertThat(valid)
                .isTrue();
    }

    @Test
    void shouldRejectTokenForDifferentUsername() {

        String token =
                jwtService.generateToken(
                        "admin",
                        "ADMIN"
                );

        boolean valid =
                jwtService.isTokenValid(
                        token,
                        "customer"
                );

        assertThat(valid)
                .isFalse();
    }

    @Test
    void shouldRejectExpiredToken() throws InterruptedException {

        JwtService shortLivedJwtService =
                new JwtService(
                        SECRET,
                        1L
                );

        String token =
                shortLivedJwtService.generateToken(
                        "admin",
                        "ADMIN"
                );

        Thread.sleep(20);

        boolean valid =
                shortLivedJwtService.isTokenValid(
                        token,
                        "admin"
                );

        assertThat(valid)
                .isFalse();
    }
}