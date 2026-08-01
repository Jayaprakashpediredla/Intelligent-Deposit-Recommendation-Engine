package com.nexuswealth.common.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class AuditableEntityTest {

    private static class TestAuditableEntity extends AuditableEntity {
    }

    @Test
    @DisplayName("Should set audit fields")
    void shouldSetAuditFields() {

        TestAuditableEntity entity = new TestAuditableEntity();

        Instant now = Instant.now();

        entity.setCreatedDate(now);
        entity.setLastModifiedDate(now);

        entity.setCreatedBy("SYSTEM");
        entity.setLastModifiedBy("SYSTEM");

        assertThat(entity.getCreatedDate())
                .isEqualTo(now);

        assertThat(entity.getLastModifiedDate())
                .isEqualTo(now);

        assertThat(entity.getCreatedBy())
                .isEqualTo("SYSTEM");

        assertThat(entity.getLastModifiedBy())
                .isEqualTo("SYSTEM");
    }

    @Test
    @DisplayName("Should allow updating modified fields")
    void shouldUpdateModifiedFields() {

        TestAuditableEntity entity = new TestAuditableEntity();

        Instant created = Instant.now();
        Instant modified = created.plusSeconds(300);

        entity.setCreatedDate(created);
        entity.setLastModifiedDate(modified);

        entity.setCreatedBy("SYSTEM");
        entity.setLastModifiedBy("ADMIN");

        assertThat(entity.getLastModifiedDate())
                .isAfter(entity.getCreatedDate());

        assertThat(entity.getLastModifiedBy())
                .isEqualTo("ADMIN");
    }
}