package com.nexuswealth.customer.controller;

import com.nexuswealth.customer.dto.request.CreateCustomerRequest;
import com.nexuswealth.customer.dto.request.UpdateCustomerRequest;
import com.nexuswealth.customer.dto.response.CustomerResponse;
import com.nexuswealth.customer.dto.response.CustomerSummaryResponse;
import com.nexuswealth.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {

        log.debug("Received request to create customer");

        CustomerResponse response =
                customerService.createCustomer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable UUID id) {

        log.debug(
                "Received request to fetch customer. id={}",
                id
        );

        return ResponseEntity.ok(
                customerService.getCustomerById(id)
        );
    }

    @GetMapping("/number/{customerNumber}")
    public ResponseEntity<CustomerResponse> getCustomerByNumber(
            @PathVariable String customerNumber) {

        log.debug(
                "Received request to fetch customer by customerNumber={}",
                customerNumber
        );

        return ResponseEntity.ok(
                customerService.getCustomerByNumber(customerNumber)
        );
    }

    @GetMapping
    public ResponseEntity<List<CustomerSummaryResponse>>
    getAllCustomers() {

        log.debug("Received request to fetch customers");

        return ResponseEntity.ok(
                customerService.getAllCustomers()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCustomerRequest request) {

        log.debug(
                "Received request to update customer. id={}",
                id
        );

        return ResponseEntity.ok(
                customerService.updateCustomer(id, request)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable UUID id) {

        log.debug(
                "Received request to delete customer. id={}",
                id
        );

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
}