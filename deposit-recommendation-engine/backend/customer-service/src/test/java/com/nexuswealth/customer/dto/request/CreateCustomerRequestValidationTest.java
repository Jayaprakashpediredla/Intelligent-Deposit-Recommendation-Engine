package com.nexuswealth.customer.dto.request;

import com.nexuswealth.common.enums.CommunicationChannel;
import com.nexuswealth.common.enums.EmploymentType;
import com.nexuswealth.common.enums.InvestmentTenure;
import com.nexuswealth.common.enums.RiskProfile;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCustomerRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should pass validation for valid customer request")
    void shouldPassValidationForValidRequest() {

        CreateCustomerRequest request = createValidRequest();

        Set<ConstraintViolation<CreateCustomerRequest>> violations =
                validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when email is invalid")
    void shouldFailWhenEmailIsInvalid() {

        CreateCustomerRequest request = createValidRequest();
        request.setEmail("invalid-email");

        Set<ConstraintViolation<CreateCustomerRequest>> violations =
                validator.validate(request);

        assertThat(violations)
                .anyMatch(violation ->
                        violation.getPropertyPath().toString().equals("email"));
    }

    @Test
    @DisplayName("Should fail validation when mobile number is invalid")
    void shouldFailWhenMobileNumberIsInvalid() {

        CreateCustomerRequest request = createValidRequest();
        request.setMobileNumber("12345");

        Set<ConstraintViolation<CreateCustomerRequest>> violations =
                validator.validate(request);

        assertThat(violations)
                .anyMatch(violation ->
                        violation.getPropertyPath().toString()
                                .equals("mobileNumber"));
    }

    @Test
    @DisplayName("Should fail validation when date of birth is in future")
    void shouldFailWhenDateOfBirthIsInFuture() {

        CreateCustomerRequest request = createValidRequest();
        request.setDateOfBirth(LocalDate.now().plusYears(1));

        Set<ConstraintViolation<CreateCustomerRequest>> violations =
                validator.validate(request);

        assertThat(violations)
                .anyMatch(violation ->
                        violation.getPropertyPath().toString()
                                .equals("dateOfBirth"));
    }

    @Test
    @DisplayName("Should fail validation when annual income is negative")
    void shouldFailWhenAnnualIncomeIsNegative() {

        CreateCustomerRequest request = createValidRequest();
        request.setAnnualIncome(BigDecimal.valueOf(-1000));

        Set<ConstraintViolation<CreateCustomerRequest>> violations =
                validator.validate(request);

        assertThat(violations)
                .anyMatch(violation ->
                        violation.getPropertyPath().toString()
                                .equals("annualIncome"));
    }

    @Test
    @DisplayName("Should fail validation when first name is blank")
    void shouldFailWhenFirstNameIsBlank() {

        CreateCustomerRequest request = createValidRequest();
        request.setFirstName("");

        Set<ConstraintViolation<CreateCustomerRequest>> violations =
                validator.validate(request);

        assertThat(violations)
                .anyMatch(violation ->
                        violation.getPropertyPath().toString()
                                .equals("firstName"));
    }

    private CreateCustomerRequest createValidRequest() {

        return CreateCustomerRequest.builder()
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
                .preferredCommunicationChannel(
                        CommunicationChannel.EMAIL
                )
                .build();
    }
}