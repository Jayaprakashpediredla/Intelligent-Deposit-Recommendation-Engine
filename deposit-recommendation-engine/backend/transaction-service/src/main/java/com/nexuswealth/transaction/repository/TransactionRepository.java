package com.nexuswealth.transaction.repository;

import com.nexuswealth.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReference(
            String transactionReference
    );

    List<Transaction> findByCustomerId(Long customerId);

    List<Transaction> findByCustomerIdOrderByTransactionDateDesc(
            Long customerId
    );

    boolean existsByTransactionReference(
            String transactionReference
    );
}