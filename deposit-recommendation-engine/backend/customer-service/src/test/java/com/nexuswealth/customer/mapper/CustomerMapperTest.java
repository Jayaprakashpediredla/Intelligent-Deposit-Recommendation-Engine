package com.nexuswealth.customer.mapper;

import com.nexuswealth.common.enums.CommunicationChannel;
import com.nexuswealth.common.enums.CustomerStatus;
import com.nexuswealth.common.enums.EmploymentType;
import com.nexuswealth.common.enums.InvestmentTenure;
import com.nexuswealth.common.enums.RiskProfile;
import com.nexuswealth.customer.dto.request.CreateCustomerRequest;
import com.nexuswealth.customer.dto.request.UpdateCustomerRequest;
import com.nexuswealth.customer.dto.response.CustomerResponse;
import com.nexuswealth.customer.dto.response.CustomerSummaryResponse;
import com.nexuswealth.customer.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        customerMapper = Mappers.getMapper(CustomerMapper.class);
    }

    @Test
    @DisplayName("Should map create request to customer entity")
    void shouldMapCreateRequestToEntity() {

        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .mobileNumber("9876543210")
                .dateOfBirth(LocalDate.of(1995, 5, 10))
                .occupation("Software Engineer")
                .annualIncome(BigDecimal.valueOf(1200000))
                .monthlyIncome(BigDecimal.valueOf(100000))
                .employmentType(EmploymentType.SALARIED)
                .riskProfile(RiskProfile.MEDIUM)
                .preferredInvestmentTenure(InvestmentTenure.LONG_TERM)
                .preferredCommunicationChannel(CommunicationChannel.EMAIL)
                .build();

        Customer customer = customerMapper.toEntity(request);

        assertThat(customer).isNotNull();

        assertThat(customer.getFirstName()).isEqualTo("John");
        assertThat(customer.getLastName()).isEqualTo("Doe");
        assertThat(customer.getEmail())
                .isEqualTo("john.doe@example.com");

        assertThat(customer.getMonthlyIncome())
                .isEqualByComparingTo("100000");

        assertThat(customer.getEmploymentType())
                .isEqualTo(EmploymentType.SALARIED);

        assertThat(customer.getRiskProfile())
                .isEqualTo(RiskProfile.MEDIUM);

        // Server-managed fields must not come from request mapping.
        assertThat(customer.getId()).isNull();
        assertThat(customer.getCustomerNumber()).isNull();
        assertThat(customer.getStatus()).isNull();
    }

    @Test
    @DisplayName("Should map customer entity to detailed response")
    void shouldMapEntityToResponse() {

        Customer customer = Customer.builder()
                .customerNumber("CUST1001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
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

        CustomerResponse response =
                customerMapper.toResponse(customer);

        assertThat(response).isNotNull();
        assertThat(response.getCustomerNumber())
                .isEqualTo("CUST1001");
        assertThat(response.getFirstName())
                .isEqualTo("John");
        assertThat(response.getRiskProfile())
                .isEqualTo(RiskProfile.MEDIUM);
        assertThat(response.getStatus())
                .isEqualTo(CustomerStatus.ACTIVE);
        assertThat(response.getKycVerified()).isTrue();
    }

    @Test
    @DisplayName("Should map customer entity to summary response")
    void shouldMapEntityToSummaryResponse() {

        Customer customer = Customer.builder()
                .customerNumber("CUST1001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .mobileNumber("9876543210")
                .riskProfile(RiskProfile.MEDIUM)
                .status(CustomerStatus.ACTIVE)
                .build();

        CustomerSummaryResponse response =
                customerMapper.toSummaryResponse(customer);

        assertThat(response).isNotNull();

        assertThat(response.getCustomerNumber())
                .isEqualTo("CUST1001");

        assertThat(response.getFullName())
                .isEqualTo("John Doe");

        assertThat(response.getRiskProfile())
                .isEqualTo(RiskProfile.MEDIUM);

        assertThat(response.getStatus())
                .isEqualTo(CustomerStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should update allowed customer fields")
    void shouldUpdateExistingCustomer() {

        Customer existingCustomer = Customer.builder()
                .customerNumber("CUST1001")
                .firstName("John")
                .lastName("Doe")
                .email("john.old@example.com")
                .mobileNumber("9876543210")
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

        UpdateCustomerRequest request =
                UpdateCustomerRequest.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.new@example.com")
                        .mobileNumber("9876543210")
                        .dateOfBirth(LocalDate.of(1995, 5, 10))
                        .occupation("Senior Software Engineer")
                        .annualIncome(BigDecimal.valueOf(1500000))
                        .monthlyIncome(BigDecimal.valueOf(125000))
                        .employmentType(EmploymentType.SALARIED)
                        .riskProfile(RiskProfile.HIGH)
                        .preferredInvestmentTenure(
                                InvestmentTenure.LONG_TERM
                        )
                        .preferredCommunicationChannel(
                                CommunicationChannel.EMAIL
                        )
                        .build();

        customerMapper.updateEntity(request, existingCustomer);

        assertThat(existingCustomer.getCustomerNumber())
                .isEqualTo("CUST1001");

        assertThat(existingCustomer.getStatus())
                .isEqualTo(CustomerStatus.ACTIVE);

        assertThat(existingCustomer.getKycVerified()).isTrue();

        assertThat(existingCustomer.getEmail())
                .isEqualTo("john.new@example.com");

        assertThat(existingCustomer.getOccupation())
                .isEqualTo("Senior Software Engineer");

        assertThat(existingCustomer.getAnnualIncome())
                .isEqualByComparingTo("1500000");

        assertThat(existingCustomer.getRiskProfile())
                .isEqualTo(RiskProfile.HIGH);
    }
}