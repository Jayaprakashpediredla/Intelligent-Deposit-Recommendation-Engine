package com.nexuswealth.customer.dto.response;

import com.nexuswealth.common.enums.CustomerStatus;
import com.nexuswealth.common.enums.RiskProfile;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Summary information for a customer")
public class CustomerSummaryResponse {

    @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(example = "CUST1001")
    private String customerNumber;

    @Schema(example = "John Doe")
    private String fullName;

    @Schema(example = "john.doe@example.com")
    private String email;

    @Schema(example = "9876543210")
    private String mobileNumber;

    @Schema(example = "MEDIUM")
    private RiskProfile riskProfile;

    @Schema(example = "ACTIVE")
    private CustomerStatus status;
}