package com.nexuswealth.transaction.mapper;

import com.nexuswealth.transaction.dto.request.CreateTransactionRequest;
import com.nexuswealth.transaction.dto.request.UpdateTransactionRequest;
import com.nexuswealth.transaction.dto.response.TransactionResponse;
import com.nexuswealth.transaction.dto.response.TransactionSummaryResponse;
import com.nexuswealth.transaction.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toEntity(CreateTransactionRequest request) {

        return Transaction.builder()
                .customerId(request.getCustomerId())
                .transactionType(request.getTransactionType())
                .amount(request.getAmount())
                .currency(request.getCurrency().toUpperCase())
                .transactionDate(request.getTransactionDate())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(
            Transaction transaction,
            UpdateTransactionRequest request
    ) {

        if (request.getStatus() != null) {
            transaction.setStatus(request.getStatus());
        }

        if (request.getDescription() != null) {
            transaction.setDescription(request.getDescription());
        }
    }

    public TransactionResponse toResponse(Transaction transaction) {

        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionReference(
                        transaction.getTransactionReference()
                )
                .customerId(transaction.getCustomerId())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .transactionDate(transaction.getTransactionDate())
                .status(transaction.getStatus())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }

    public TransactionSummaryResponse toSummaryResponse(
            Transaction transaction
    ) {

        return TransactionSummaryResponse.builder()
                .id(transaction.getId())
                .transactionReference(
                        transaction.getTransactionReference()
                )
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .transactionDate(transaction.getTransactionDate())
                .status(transaction.getStatus())
                .build();
    }
}