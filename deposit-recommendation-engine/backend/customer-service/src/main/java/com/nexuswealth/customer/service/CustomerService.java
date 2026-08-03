package com.nexuswealth.customer.service;

import com.nexuswealth.customer.dto.request.CreateCustomerRequest;
import com.nexuswealth.customer.dto.request.UpdateCustomerRequest;
import com.nexuswealth.customer.dto.response.CustomerResponse;
import com.nexuswealth.customer.dto.response.CustomerSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerResponse createCustomer(CreateCustomerRequest request);

    CustomerResponse getCustomerById(UUID id);

    CustomerResponse getCustomerByNumber(String customerNumber);

    List<CustomerSummaryResponse> getAllCustomers();

    CustomerResponse updateCustomer(
            UUID id,
            UpdateCustomerRequest request
    );

    void deleteCustomer(UUID id);
}