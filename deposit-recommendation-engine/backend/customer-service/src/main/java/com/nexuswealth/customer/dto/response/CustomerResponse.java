package com.nexuswealth.customer.dto.response;

import com.nexuswealth.common.enums.CommunicationChannel;
import com.nexuswealth.common.enums.CustomerStatus;
import com.nexuswealth.common.enums.EmploymentType;
import com.nexuswealth.common.enums.InvestmentTenure;
import com.nexuswealth.common.enums.RiskProfile;
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
public class CustomerResponse {

    private UUID id;

    private String customerNumber;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private LocalDate dateOfBirth;

    private String occupation;

    private BigDecimal annualIncome;

    private BigDecimal monthlyIncome;

    private EmploymentType employmentType;

    private RiskProfile riskProfile;

    private InvestmentTenure preferredInvestmentTenure;

    private CommunicationChannel preferredCommunicationChannel;

    private CustomerStatus status;

    private Boolean kycVerified;

    private Instant createdDate;

    private Instant lastModifiedDate;
}