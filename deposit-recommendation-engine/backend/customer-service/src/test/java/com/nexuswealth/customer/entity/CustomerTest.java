package com.nexuswealth.customer.entity;

import com.nexuswealth.common.enums.CustomerStatus;
import com.nexuswealth.common.enums.RiskProfile;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    @Test
    void shouldCreateCustomer() {

        Customer customer = Customer.builder()
                .customerNumber("CUST000001")
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("9876543210")
                .dateOfBirth(LocalDate.of(1995, 5, 10))
                .occupation("Software Engineer")
                .annualIncome(BigDecimal.valueOf(1200000))
                .riskProfile(RiskProfile.MEDIUM)
                .status(CustomerStatus.ACTIVE)
                .kycVerified(true)
                .build();

        assertThat(customer.getCustomerNumber()).isEqualTo("CUST000001");
        assertThat(customer.getRiskProfile()).isEqualTo(RiskProfile.MEDIUM);
        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
        assertThat(customer.getKycVerified()).isTrue();

    }
}
