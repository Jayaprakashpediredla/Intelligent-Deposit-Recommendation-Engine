package com.nexuswealth.customer.repository;

import com.nexuswealth.common.enums.CommunicationChannel;
import com.nexuswealth.common.enums.CustomerStatus;
import com.nexuswealth.common.enums.EmploymentType;
import com.nexuswealth.common.enums.InvestmentTenure;
import com.nexuswealth.common.enums.RiskProfile;
import com.nexuswealth.customer.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should save customer successfully")
    void shouldSaveCustomerSuccessfully() {

        Customer customer = Customer.builder()
                .customerNumber("CUST1001")
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

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getCustomerNumber()).isEqualTo("CUST1001");
        assertThat(savedCustomer.getEmail()).isEqualTo("john@example.com");
        assertThat(savedCustomer.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
        assertThat(savedCustomer.getRiskProfile()).isEqualTo(RiskProfile.MEDIUM);
    }
}