package com.nexuswealth.common.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DateUtilsTest {

    @Test
    void shouldFormatInstant() {

        Instant instant = Instant.parse("2026-07-29T10:15:30Z");

        String formatted = DateUtils.format(instant);

        assertThat(formatted)
                .isEqualTo("2026-07-29T10:15:30Z");
    }
}