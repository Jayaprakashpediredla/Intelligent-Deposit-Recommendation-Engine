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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        validateDuplicateCustomer(request);

        Customer customer = customerMapper.toEntity(request);

        customer.setCustomerNumber(generateCustomerNumber());
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setKycVerified(false);

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(UUID id) {

        Customer customer = findCustomerById(id);

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerByNumber(String customerNumber) {

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

        Customer existingCustomer = findCustomerById(id);

        validateUpdateDuplicates(existingCustomer, request);

        customerMapper.updateEntity(request, existingCustomer);

        Customer updatedCustomer =
                customerRepository.save(existingCustomer);

        return customerMapper.toResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {

        Customer customer = findCustomerById(id);

        customerRepository.delete(customer);
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
            throw new DuplicateResourceException(
                    "Customer already exists with email: "
                            + request.getEmail()
            );
        }

        if (customerRepository.existsByMobileNumber(
                request.getMobileNumber())) {

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