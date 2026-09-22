package com.example.fraud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModelPredictionRequest(
        double amount,
        int hour,
        @JsonProperty("distance_from_home_km") int distanceFromHomeKm,
        @JsonProperty("foreign_transaction") boolean foreignTransaction,
        @JsonProperty("online_transaction") boolean onlineTransaction,
        @JsonProperty("transactions_last_24h") int transactionsLast24h
) {}

