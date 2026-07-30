package com.nexuswealth.customer.entity;

import com.nexuswealth.common.enums.*;
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
                .monthlyIncome(BigDecimal.valueOf(100000))
                .employmentType(EmploymentType.SALARIED)
                .riskProfile(RiskProfile.MEDIUM)
                .preferredInvestmentTenure(InvestmentTenure.LONG_TERM)
                .preferredCommunicationChannel(CommunicationChannel.EMAIL)
                .status(CustomerStatus.ACTIVE)
                .kycVerified(true)
                .build();

        assertThat(customer.getCustomerNumber())
                .isEqualTo("CUST000001");

        assertThat(customer.getRiskProfile())
                .isEqualTo(RiskProfile.MEDIUM);

        assertThat(customer.getStatus())
                .isEqualTo(CustomerStatus.ACTIVE);

        assertThat(customer.getKycVerified())
                .isTrue();

        assertThat(customer.getMonthlyIncome())
                .isEqualByComparingTo(BigDecimal.valueOf(100000));

        assertThat(customer.getEmploymentType())
                .isEqualTo(EmploymentType.SALARIED);

        assertThat(customer.getPreferredInvestmentTenure())
                .isEqualTo(InvestmentTenure.LONG_TERM);

        assertThat(customer.getPreferredCommunicationChannel())
                .isEqualTo(CommunicationChannel.EMAIL);

    }
}
