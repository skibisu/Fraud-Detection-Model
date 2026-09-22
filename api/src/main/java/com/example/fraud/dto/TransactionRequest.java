package com.example.fraud.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
        @NotNull @DecimalMin(value = "0.01") Double amount,
        @NotNull @Min(0) @Max(23) Integer hour,
        @NotNull @Min(0) Integer distanceFromHomeKm,
        @NotNull Boolean foreignTransaction,
        @NotNull Boolean onlineTransaction,
        @NotNull @Min(0) Integer transactionsLast24h
) {}

