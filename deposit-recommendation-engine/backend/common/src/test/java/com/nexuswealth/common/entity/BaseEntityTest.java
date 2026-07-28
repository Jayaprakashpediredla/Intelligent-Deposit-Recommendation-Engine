package com.nexuswealth.common.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BaseEntityTest {

    private static class TestEntity extends BaseEntity {
    }

    @Test
    @DisplayName("Should set UUID identifier")
    void shouldSetId() {

        TestEntity entity = new TestEntity();
        UUID id = UUID.randomUUID();
        entity.setId(id);
        assertThat(entity.getId())
                .isEqualTo(id);

    }

    @Test
    @DisplayName("Should set version")
    void shouldSetVersion() {

        TestEntity entity = new TestEntity();
        entity.setVersion(2L);
        assertThat(entity.getVersion()).isEqualTo(2L);
    }

    @Test
    @DisplayName("Should create different UUIDs")
    void shouldGenerateUniqueIds() {

        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        assertThat(id1)
                .isNotEqualTo(id2);

    }
}