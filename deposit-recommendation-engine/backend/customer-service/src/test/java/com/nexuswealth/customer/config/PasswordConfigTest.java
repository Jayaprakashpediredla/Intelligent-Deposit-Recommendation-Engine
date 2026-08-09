package com.nexuswealth.customer.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordConfigTest {

    private final PasswordEncoder passwordEncoder =
            new PasswordConfig().passwordEncoder();

    @Test
    void shouldEncodePassword() {

        String rawPassword =
                "Password@123";

        String encodedPassword =
                passwordEncoder.encode(rawPassword);

        assertThat(encodedPassword)
                .isNotBlank();

        assertThat(encodedPassword)
                .isNotEqualTo(rawPassword);
    }

    @Test
    void shouldMatchCorrectPassword() {

        String rawPassword =
                "Password@123";

        String encodedPassword =
                passwordEncoder.encode(rawPassword);

        assertThat(
                passwordEncoder.matches(
                        rawPassword,
                        encodedPassword
                )
        ).isTrue();
    }

    @Test
    void shouldRejectIncorrectPassword() {

        String encodedPassword =
                passwordEncoder.encode(
                        "Password@123"
                );

        assertThat(
                passwordEncoder.matches(
                        "WrongPassword",
                        encodedPassword
                )
        ).isFalse();
    }
}