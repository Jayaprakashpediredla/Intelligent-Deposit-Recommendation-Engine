package com.nexuswealth.customer.service.impl;

import com.nexuswealth.common.enums.CustomerStatus;
import com.nexuswealth.customer.dto.request.CreateCustomerRequest;
import com.nexuswealth.customer.dto.request.UpdateCustomerRequest;
import com.nexuswealth.customer.dto.response.CustomerResponse;
import com.nexuswealth.customer.dto.response.CustomerSummaryResponse;
import com.nexuswealth.customer.entity.Customer;
import com.nexuswealth.customer.exception.DuplicateResourceException;
import com.nexuswealth.customer.exception.ResourceNotFoundException;
import com.nexuswealth.customer.mapper.CustomerMapper;
import com.nexuswealth.customer.repository.CustomerRepository;
import com.nexuswealth.customer.service.CustomerService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        log.info("Creating customer with email: {}", maskEmail(request.getEmail()));

        validateDuplicateCustomer(request);

        Customer customer = customerMapper.toEntity(request);

        customer.setCustomerNumber(generateCustomerNumber());
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setKycVerified(false);

        Customer savedCustomer = customerRepository.save(customer);

        log.info("Customer created successfully. customerNumber={}", savedCustomer.getCustomerNumber());

        return customerMapper.toResponse(savedCustomer);
    }

    private String maskEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") @Size(max = 100, message = "Email cannot exceed 100 characters") String email) {
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return email;
        }
        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex);
        return localPart.charAt(0) + "****" + localPart.charAt(localPart.length() - 1) + domainPart;
    }

    /*private String maskEmail(String email) {

        if (email == null || !email.contains("@")) {
            return "***";
        }

        int atIndex = email.indexOf('@');

        if (atIndex <= 1) {
            return "***" + email.substring(atIndex);
        }

        return email.charAt(0)
                + "***"
                + email.substring(atIndex);
    }*/

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(UUID id) {

        log.debug("Fetching customer by id: {}", id);

        Customer customer = findCustomerById(id);

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerByNumber(String customerNumber) {

        log.debug("Fetching customer by number: {}", customerNumber);

        Customer customer = customerRepository
                .findByCustomerNumber(customerNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with customer number: "
                                        + customerNumber
                        )
                );

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerSummaryResponse> getAllCustomers() {

        log.debug("Fetching all customers");

        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::toSummaryResponse)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(
            UUID id,
            UpdateCustomerRequest request
    ) {

        log.debug("Updating customer with id: {}", id);

        Customer existingCustomer = findCustomerById(id);

        validateUpdateDuplicates(existingCustomer, request);

        customerMapper.updateEntity(request, existingCustomer);

        Customer updatedCustomer =
                customerRepository.save(existingCustomer);

        log.info("Customer updated successfully. customerNumber={}", updatedCustomer.getCustomerNumber());

        return customerMapper.toResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        log.debug("Deleting customer with id: {}", id);

        Customer customer = findCustomerById(id);

        customerRepository.delete(customer);
        log.info("Customer deleted successfully. customerNumber={}", customer.getCustomerNumber());
    }

    private Customer findCustomerById(UUID id) {

        return customerRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + id
                        )
                );
    }

    private void validateDuplicateCustomer(
            CreateCustomerRequest request
    ) {

        if (customerRepository.existsByEmail(request.getEmail())) {

            log.warn("Customer creation rejected because email already exists: {}", maskEmail(request.getEmail()));
            throw new DuplicateResourceException(
                    "Customer already exists with email: "
                            + request.getEmail()
            );
        }

        if (customerRepository.existsByMobileNumber(
                request.getMobileNumber())) {

            log.warn("Customer creation rejected because mobile number already exists");

            throw new DuplicateResourceException(
                    "Customer already exists with mobile number: "
                            + request.getMobileNumber()
            );
        }
    }

    private void validateUpdateDuplicates(
            Customer existingCustomer,
            UpdateCustomerRequest request
    ) {

        if (request.getEmail() != null
                && !request.getEmail()
                .equalsIgnoreCase(existingCustomer.getEmail())
                && customerRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Customer already exists with email: "
                            + request.getEmail()
            );
        }

        if (request.getMobileNumber() != null
                && !request.getMobileNumber()
                .equals(existingCustomer.getMobileNumber())
                && customerRepository.existsByMobileNumber(
                request.getMobileNumber())) {

            throw new DuplicateResourceException(
                    "Customer already exists with mobile number: "
                            + request.getMobileNumber()
            );
        }
    }

    private String generateCustomerNumber() {

        String customerNumber;

        do {
            customerNumber =
                    "CUST-" +
                            UUID.randomUUID()
                                    .toString()
                                    .substring(0, 8)
                                    .toUpperCase();

        } while (customerRepository
                .existsByCustomerNumber(customerNumber));

        return customerNumber;
    }
}