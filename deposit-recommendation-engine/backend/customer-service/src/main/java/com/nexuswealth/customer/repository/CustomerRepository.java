package com.nexuswealth.customer.repository;

import com.nexuswealth.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends
        JpaRepository<Customer, UUID>,
        JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByCustomerNumber(String customerNumber);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByMobileNumber(String mobileNumber);

    boolean existsByCustomerNumber(String customerNumber);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

}