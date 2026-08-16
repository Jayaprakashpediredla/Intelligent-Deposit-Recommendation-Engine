package com.nexuswealth.transaction.dto.request;

import com.nexuswealth.transaction.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreateTransactionRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    @NotNull(message = "Amount is required")
    @DecimalMin(
            value = "0.01",
            message = "Amount must be greater than zero"
    )
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Size(
            min = 3,
            max = 3,
            message = "Currency must be 3 characters"
    )
    private String currency;

    private LocalDateTime transactionDate;

    @Size(
            max = 255,
            message = "Description cannot exceed 255 characters"
    )
    private String description;
}