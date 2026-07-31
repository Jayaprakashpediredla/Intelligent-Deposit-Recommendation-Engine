package com.nexuswealth.customer.dto.request;

import com.nexuswealth.common.enums.CommunicationChannel;
import com.nexuswealth.common.enums.EmploymentType;
import com.nexuswealth.common.enums.InvestmentTenure;
import com.nexuswealth.common.enums.RiskProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {

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
}