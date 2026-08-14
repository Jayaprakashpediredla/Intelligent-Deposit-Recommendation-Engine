package com.nexuswealth.transaction.repository;

import com.nexuswealth.transaction.entity.Transaction;
import com.nexuswealth.transaction.enums.TransactionStatus;
import com.nexuswealth.transaction.enums.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("Should save transaction successfully")
    void shouldSaveTransaction() {

        Transaction transaction = Transaction.builder()
                .transactionReference("TXN1001")
                .customerId(1L)
                .transactionType(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(50000))
                .currency("INR")
                .transactionDate(LocalDateTime.now())
                .status(TransactionStatus.COMPLETED)
                .description("Initial deposit")
                .build();

        Transaction savedTransaction =
                repository.save(transaction);

        assertThat(savedTransaction.getId()).isNotNull();
        assertThat(savedTransaction.getTransactionReference())
                .isEqualTo("TXN1001");
        assertThat(savedTransaction.getCustomerId())
                .isEqualTo(1L);
    }
}