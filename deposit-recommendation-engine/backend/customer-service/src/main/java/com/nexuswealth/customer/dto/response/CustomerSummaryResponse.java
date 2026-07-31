package com.nexuswealth.customer.dto.response;

import com.nexuswealth.common.enums.CustomerStatus;
import com.nexuswealth.common.enums.RiskProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSummaryResponse {

    private UUID id;

    private String customerNumber;

    private String fullName;

    private String email;

    private String mobileNumber;

    private RiskProfile riskProfile;

    private CustomerStatus status;
}