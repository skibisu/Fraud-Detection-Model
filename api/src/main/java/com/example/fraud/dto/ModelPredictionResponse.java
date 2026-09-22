package com.example.fraud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModelPredictionResponse(
        @JsonProperty("fraud_probability") double fraudProbability,
        @JsonProperty("is_fraud") boolean fraud,
        @JsonProperty("model_version") String modelVersion
) {}

