package com.nexuswealth.customer.service.impl;

import com.nexuswealth.common.enums.CustomerStatus;
import com.nexuswealth.customer.dto.request.CreateCustomerRequest;
import com.nexuswealth.customer.dto.response.CustomerResponse;
import com.nexuswealth.customer.entity.Customer;
import com.nexuswealth.customer.exception.DuplicateResourceException;
import com.nexuswealth.customer.exception.ResourceNotFoundException;
import com.nexuswealth.customer.mapper.CustomerMapper;
import com.nexuswealth.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private UUID customerId;
    private Customer customer;

    @BeforeEach
    void setUp() {

        customerId = UUID.randomUUID();

        customer = Customer.builder()
                .customerNumber("CUST-12345678")
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("9876543210")
                .status(CustomerStatus.ACTIVE)
                .kycVerified(false)
                .build();
        customer.setId(customerId);
    }

    @Test
    @DisplayName("Should create customer successfully")
    void shouldCreateCustomerSuccessfully() {

        CreateCustomerRequest request =
                CreateCustomerRequest.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("john@example.com")
                        .mobileNumber("9876543210")
                        .build();

        Customer newCustomer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("9876543210")
                .build();

        CustomerResponse response =
                CustomerResponse.builder()
                        .id(customerId)
                        .customerNumber("CUST-12345678")
                        .firstName("John")
                        .lastName("Doe")
                        .email("john@example.com")
                        .build();

        when(customerRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(customerRepository.existsByMobileNumber(
                request.getMobileNumber()))
                .thenReturn(false);

        // Customer-number generator checks uniqueness.
        when(customerRepository.existsByCustomerNumber(anyString()))
                .thenReturn(false);

        when(customerMapper.toEntity(request))
                .thenReturn(newCustomer);

        when(customerRepository.save(newCustomer))
                .thenReturn(customer);

        when(customerMapper.toResponse(customer))
                .thenReturn(response);

        CustomerResponse result =
                customerService.createCustomer(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(customerId);
        assertThat(result.getFirstName()).isEqualTo("John");

        verify(customerRepository).save(newCustomer);

        assertThat(newCustomer.getCustomerNumber()).isNotBlank();
        assertThat(newCustomer.getCustomerNumber())
                .startsWith("CUST-");

        assertThat(newCustomer.getStatus())
                .isEqualTo(CustomerStatus.ACTIVE);

        assertThat(newCustomer.getKycVerified()).isFalse();
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailExists() {

        CreateCustomerRequest request =
                CreateCustomerRequest.builder()
                        .email("john@example.com")
                        .mobileNumber("9876543210")
                        .build();

        when(customerRepository.existsByEmail(
                "john@example.com"))
                .thenReturn(true);

        assertThatThrownBy(() ->
                customerService.createCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("john@example.com");

        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return customer by id")
    void shouldReturnCustomerById() {

        CustomerResponse response =
                CustomerResponse.builder()
                        .id(customerId)
                        .customerNumber("CUST-12345678")
                        .firstName("John")
                        .build();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(customerMapper.toResponse(customer))
                .thenReturn(response);

        CustomerResponse result =
                customerService.getCustomerById(customerId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(customerId);

        verify(customerRepository).findById(customerId);
    }

    @Test
    @DisplayName("Should throw exception when customer does not exist")
    void shouldThrowExceptionWhenCustomerNotFound() {

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                customerService.getCustomerById(customerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(customerId.toString());

        verify(customerMapper, never())
                .toResponse(any());
    }

    @Test
    @DisplayName("Should delete customer successfully")
    void shouldDeleteCustomerSuccessfully() {

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        customerService.deleteCustomer(customerId);

        verify(customerRepository).findById(customerId);
        verify(customerRepository).delete(customer);
    }
}