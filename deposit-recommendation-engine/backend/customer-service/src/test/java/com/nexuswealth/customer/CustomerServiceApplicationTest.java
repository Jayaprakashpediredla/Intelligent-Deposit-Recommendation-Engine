package com.nexuswealth.customer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerServiceApplicationTest {

    @Test
    void applicationClassShouldExist() {
        assertThat(CustomerServiceApplication.class).isNotNull();
    }
}