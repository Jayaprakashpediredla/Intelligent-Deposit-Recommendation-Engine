package com.nexuswealth.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexuswealth.common.enums.*;
import com.nexuswealth.customer.dto.request.CreateCustomerRequest;
import com.nexuswealth.customer.dto.request.UpdateCustomerRequest;
import com.nexuswealth.customer.dto.response.CustomerResponse;
import com.nexuswealth.customer.dto.response.CustomerSummaryResponse;
import com.nexuswealth.customer.exception.DuplicateResourceException;
import com.nexuswealth.customer.exception.GlobalExceptionHandler;
import com.nexuswealth.customer.exception.ResourceNotFoundException;
import com.nexuswealth.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@Import(GlobalExceptionHandler.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService customerService;

    private UUID customerId;

    private CreateCustomerRequest createRequest;

    private UpdateCustomerRequest updateRequest;

    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {

        customerId = UUID.randomUUID();

        createRequest = CreateCustomerRequest.builder()
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
                .preferredInvestmentTenure(
                        InvestmentTenure.LONG_TERM
                )
                .preferredCommunicationChannel(
                        CommunicationChannel.EMAIL
                )
                .build();

        updateRequest = UpdateCustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.updated@example.com")
                .mobileNumber("9876543210")
                .dateOfBirth(LocalDate.of(1995, 5, 10))
                .occupation("Senior Software Engineer")
                .annualIncome(BigDecimal.valueOf(1500000))
                .monthlyIncome(BigDecimal.valueOf(125000))
                .employmentType(EmploymentType.SALARIED)
                .riskProfile(RiskProfile.MEDIUM)
                .preferredInvestmentTenure(
                        InvestmentTenure.LONG_TERM
                )
                .preferredCommunicationChannel(
                        CommunicationChannel.EMAIL
                )
                .build();

        customerResponse = CustomerResponse.builder()
                .id(customerId)
                .customerNumber("CUST-12345678")
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
                .preferredInvestmentTenure(
                        InvestmentTenure.LONG_TERM
                )
                .preferredCommunicationChannel(
                        CommunicationChannel.EMAIL
                )
                .status(CustomerStatus.ACTIVE)
                .kycVerified(false)
                .build();
    }

    // -------------------------------------------------------
    // POST
    // -------------------------------------------------------

    @Test
    @DisplayName("Should create customer and return 201")
    void shouldCreateCustomer() throws Exception {

        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenReturn(customerResponse);

        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                createRequest
                                        )
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(
                                MediaType.APPLICATION_JSON
                        ))
                .andExpect(jsonPath("$.id")
                        .value(customerId.toString()))
                .andExpect(jsonPath("$.customerNumber")
                        .value("CUST-12345678"))
                .andExpect(jsonPath("$.firstName")
                        .value("John"))
                .andExpect(jsonPath("$.email")
                        .value("john.doe@example.com"));

        verify(customerService)
                .createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    @DisplayName("Should return 409 when customer already exists")
    void shouldReturnConflictForDuplicateCustomer()
            throws Exception {

        when(customerService.createCustomer(
                any(CreateCustomerRequest.class)))
                .thenThrow(
                        new DuplicateResourceException(
                                "Customer already exists"
                        )
                );

        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                createRequest
                                        )
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error")
                        .value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("Customer already exists"))
                .andExpect(jsonPath("$.path")
                        .value("/api/v1/customers"));
    }

    // -------------------------------------------------------
    // GET BY ID
    // -------------------------------------------------------

    @Test
    @DisplayName("Should return customer by id")
    void shouldGetCustomerById() throws Exception {

        when(customerService.getCustomerById(customerId))
                .thenReturn(customerResponse);

        mockMvc.perform(
                        get("/api/v1/customers/{id}", customerId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(customerId.toString()))
                .andExpect(jsonPath("$.customerNumber")
                        .value("CUST-12345678"))
                .andExpect(jsonPath("$.firstName")
                        .value("John"));

        verify(customerService)
                .getCustomerById(customerId);
    }

    @Test
    @DisplayName("Should return 404 when customer id does not exist")
    void shouldReturnNotFoundForMissingCustomer()
            throws Exception {

        when(customerService.getCustomerById(customerId))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Customer not found with id: "
                                        + customerId
                        )
                );

        mockMvc.perform(
                        get("/api/v1/customers/{id}", customerId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status")
                        .value(404))
                .andExpect(jsonPath("$.error")
                        .value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Customer not found with id: "
                                        + customerId
                        ));
    }

    // -------------------------------------------------------
    // GET BY CUSTOMER NUMBER
    // -------------------------------------------------------

    @Test
    @DisplayName("Should return customer by customer number")
    void shouldGetCustomerByNumber() throws Exception {

        String customerNumber = "CUST-12345678";

        when(customerService.getCustomerByNumber(customerNumber))
                .thenReturn(customerResponse);

        mockMvc.perform(
                        get(
                                "/api/v1/customers/number/{customerNumber}",
                                customerNumber
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerNumber")
                        .value(customerNumber))
                .andExpect(jsonPath("$.firstName")
                        .value("John"));

        verify(customerService)
                .getCustomerByNumber(customerNumber);
    }

    // -------------------------------------------------------
    // GET ALL
    // -------------------------------------------------------

    @Test
    @DisplayName("Should return all customers")
    void shouldGetAllCustomers() throws Exception {

        CustomerSummaryResponse summary =
                CustomerSummaryResponse.builder()
                        .customerNumber("CUST-12345678")
                        .fullName("John Doe")
                        .email("john.doe@example.com")
                        .riskProfile(RiskProfile.MEDIUM)
                        .status(CustomerStatus.ACTIVE)
                        .build();

        when(customerService.getAllCustomers())
                .thenReturn(List.of(summary));

        mockMvc.perform(
                        get("/api/v1/customers")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()")
                        .value(1))
                .andExpect(jsonPath("$[0].customerNumber")
                        .value("CUST-12345678"))
                .andExpect(jsonPath("$[0].fullName")
                        .value("John Doe"));

        verify(customerService)
                .getAllCustomers();
    }

    // -------------------------------------------------------
    // PUT
    // -------------------------------------------------------

    @Test
    @DisplayName("Should update customer")
    void shouldUpdateCustomer() throws Exception {

        CustomerResponse updatedResponse =
                CustomerResponse.builder()
                        .id(customerId)
                        .customerNumber("CUST-12345678")
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.updated@example.com")
                        .occupation("Senior Software Engineer")
                        .status(CustomerStatus.ACTIVE)
                        .kycVerified(false)
                        .build();

        when(customerService.updateCustomer(
                eq(customerId),
                any(UpdateCustomerRequest.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(
                        put("/api/v1/customers/{id}", customerId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                updateRequest
                                        )
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(customerId.toString()))
                .andExpect(jsonPath("$.email")
                        .value("john.updated@example.com"))
                .andExpect(jsonPath("$.occupation")
                        .value("Senior Software Engineer"));

        verify(customerService)
                .updateCustomer(
                        eq(customerId),
                        any(UpdateCustomerRequest.class)
                );
    }

    // -------------------------------------------------------
    // DELETE
    // -------------------------------------------------------

    @Test
    @DisplayName("Should delete customer and return 204")
    void shouldDeleteCustomer() throws Exception {

        doNothing()
                .when(customerService)
                .deleteCustomer(customerId);

        mockMvc.perform(
                        delete("/api/v1/customers/{id}", customerId)
                )
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(customerService)
                .deleteCustomer(customerId);
    }

    @Test
    @DisplayName("Should return 404 when deleting missing customer")
    void shouldReturnNotFoundWhenDeletingMissingCustomer()
            throws Exception {

        doThrow(
                new ResourceNotFoundException(
                        "Customer not found with id: "
                                + customerId
                )
        )
                .when(customerService)
                .deleteCustomer(customerId);

        mockMvc.perform(
                        delete("/api/v1/customers/{id}", customerId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status")
                        .value(404))
                .andExpect(jsonPath("$.error")
                        .value("Not Found"));
    }

    @Test
    @DisplayName("Should return 400 when create customer request is invalid")
    void shouldReturnBadRequestForInvalidCreateRequest()
            throws Exception {

        String invalidRequest = """
            {
                "firstName": "",
                "lastName": "",
                "email": "invalid-email",
                "mobileNumber": ""
            }
            """;

        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidRequest)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.message")
                        .value("Validation failed"))
                .andExpect(jsonPath("$.path")
                        .value("/api/v1/customers"))
                .andExpect(jsonPath("$.validationErrors")
                        .exists());

        verifyNoInteractions(customerService);
    }

    @Test
    @DisplayName("Should return 400 when update request is invalid")
    void shouldReturnBadRequestForInvalidUpdateRequest()
            throws Exception {

        String invalidRequest = """
            {
                "firstName": "",
                "lastName": "",
                "email": "not-an-email",
                "mobileNumber": ""
            }
            """;

        mockMvc.perform(
                        put(
                                "/api/v1/customers/{id}",
                                customerId
                        )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidRequest)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.message")
                        .value("Validation failed"))
                .andExpect(jsonPath("$.validationErrors")
                        .exists());

        verifyNoInteractions(customerService);
    }

    @Test
    @DisplayName("Should return empty list when no customers exist")
    void shouldReturnEmptyCustomerList()
            throws Exception {

        when(customerService.getAllCustomers())
                .thenReturn(List.of());

        mockMvc.perform(
                        get("/api/v1/customers")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()")
                        .value(0));

        verify(customerService)
                .getAllCustomers();
    }

}