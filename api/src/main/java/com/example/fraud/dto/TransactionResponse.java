package com.example.fraud.dto;

import com.example.fraud.entity.Transaction;
import java.time.Instant;

public record TransactionResponse(
        Long id,
        Double amount,
        Integer hour,
        Integer distanceFromHomeKm,
        Boolean foreignTransaction,
        Boolean onlineTransaction,
        Integer transactionsLast24h,
        Double fraudProbability,
        Boolean fraud,
        String modelVersion,
        Instant createdAt
) {
    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(), transaction.getAmount(), transaction.getHour(),
                transaction.getDistanceFromHomeKm(), transaction.getForeignTransaction(),
                transaction.getOnlineTransaction(), transaction.getTransactionsLast24h(),
                transaction.getFraudProbability(), transaction.getFraud(),
                transaction.getModelVersion(), transaction.getCreatedAt()
        );
    }
}

