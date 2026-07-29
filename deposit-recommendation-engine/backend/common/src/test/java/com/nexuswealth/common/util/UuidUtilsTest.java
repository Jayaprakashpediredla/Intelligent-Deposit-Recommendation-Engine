package com.nexuswealth.common.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UuidUtilsTest {

    @Test
    void shouldGenerateUuid() {

        UUID uuid = UuidUtils.generate();

        assertThat(uuid).isNotNull();
    }

    @Test
    void shouldGenerateUniqueUuids() {

        UUID first = UuidUtils.generate();
        UUID second = UuidUtils.generate();

        assertThat(first).isNotEqualTo(second);
    }
}