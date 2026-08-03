package com.nexuswealth.customer.dto.request;

import com.nexuswealth.common.enums.CommunicationChannel;
import com.nexuswealth.common.enums.EmploymentType;
import com.nexuswealth.common.enums.InvestmentTenure;
import com.nexuswealth.common.enums.RiskProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Request payload used to update an existing customer")
public class UpdateCustomerRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    @Schema(example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    @Schema(example = "Doe")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Schema(example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Mobile number must be a valid 10-digit Indian number"
    )
    @Schema(example = "9876543210")
    private String mobileNumber;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Schema(example = "1995-05-10", type = "string", format = "date")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Occupation is required")
    @Size(max = 100, message = "Occupation cannot exceed 100 characters")
    @Schema(example = "Senior Software Engineer")
    private String occupation;

    @NotNull(message = "Annual income is required")
    @Positive(message = "Annual income must be greater than zero")
    @Schema(example = "1500000")
    private BigDecimal annualIncome;

    @NotNull(message = "Monthly income is required")
    @Positive(message = "Monthly income must be greater than zero")
    @Schema(example = "125000")
    private BigDecimal monthlyIncome;

    @NotNull(message = "Employment type is required")
    @Schema(example = "SALARIED")
    private EmploymentType employmentType;

    @NotNull(message = "Risk profile is required")
    @Schema(example = "MEDIUM")
    private RiskProfile riskProfile;

    @NotNull(message = "Investment tenure is required")
    @Schema(example = "LONG_TERM")
    private InvestmentTenure preferredInvestmentTenure;

    @NotNull(message = "Preferred communication channel is required")
    @Schema(example = "EMAIL")
    private CommunicationChannel preferredCommunicationChannel;
}