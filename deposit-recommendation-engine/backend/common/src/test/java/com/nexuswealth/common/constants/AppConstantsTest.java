package com.nexuswealth.common.constants;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AppConstantsTest {

    @Test
    void shouldHaveApplicationName() {

        assertThat(AppConstants.APPLICATION_NAME)
                .isEqualTo("Intelligent Deposit Recommendation Engine");
    }
}