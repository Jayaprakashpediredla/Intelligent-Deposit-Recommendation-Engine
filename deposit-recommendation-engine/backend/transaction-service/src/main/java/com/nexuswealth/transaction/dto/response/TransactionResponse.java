package com.nexuswealth.transaction.dto.response;

import com.nexuswealth.transaction.enums.TransactionStatus;
import com.nexuswealth.transaction.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Long id;

    private String transactionReference;

    private Long customerId;

    private TransactionType transactionType;

    private BigDecimal amount;

    private String currency;

    private LocalDateTime transactionDate;

    private TransactionStatus status;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}