package com.nexuswealth.customer.dto.response;

import com.nexuswealth.common.enums.CommunicationChannel;
import com.nexuswealth.common.enums.CustomerStatus;
import com.nexuswealth.common.enums.EmploymentType;
import com.nexuswealth.common.enums.InvestmentTenure;
import com.nexuswealth.common.enums.RiskProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detailed customer information")
public class CustomerResponse {

    @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(example = "CUST1001")
    private String customerNumber;

    @Schema(example = "John")
    private String firstName;

    @Schema(example = "Doe")
    private String lastName;

    @Schema(example = "john.doe@example.com")
    private String email;

    @Schema(example = "9876543210")
    private String mobileNumber;

    @Schema(example = "1995-05-10")
    private LocalDate dateOfBirth;

    @Schema(example = "Software Engineer")
    private String occupation;

    @Schema(example = "1200000")
    private BigDecimal annualIncome;

    @Schema(example = "100000")
    private BigDecimal monthlyIncome;

    @Schema(example = "SALARIED")
    private EmploymentType employmentType;

    @Schema(example = "MEDIUM")
    private RiskProfile riskProfile;

    @Schema(example = "LONG_TERM")
    private InvestmentTenure preferredInvestmentTenure;

    @Schema(example = "EMAIL")
    private CommunicationChannel preferredCommunicationChannel;

    @Schema(example = "ACTIVE")
    private CustomerStatus status;

    @Schema(example = "true")
    private Boolean kycVerified;

    private Instant createdDate;

    private Instant lastModifiedDate;
}