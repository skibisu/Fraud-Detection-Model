package com.example.fraud.service;

import com.example.fraud.dto.*;
import com.example.fraud.entity.Transaction;
import com.example.fraud.exception.ModelServiceException;
import com.example.fraud.exception.ResourceNotFoundException;
import com.example.fraud.repository.TransactionRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final RestClient modelRestClient;

    public TransactionService(TransactionRepository repository, RestClient modelRestClient) {
        this.repository = repository;
        this.modelRestClient = modelRestClient;
    }

    public TransactionResponse predictAndSave(TransactionRequest request) {
        ModelPredictionRequest modelRequest = new ModelPredictionRequest(
                request.amount(), request.hour(), request.distanceFromHomeKm(),
                request.foreignTransaction(), request.onlineTransaction(),
                request.transactionsLast24h()
        );

        ModelPredictionResponse prediction;
        try {
            prediction = modelRestClient.post()
                    .uri("/predict")
                    .body(modelRequest)
                    .retrieve()
                    .body(ModelPredictionResponse.class);
        } catch (Exception exception) {
            throw new ModelServiceException("Fraud model service is unavailable", exception);
        }

        if (prediction == null) {
            throw new ModelServiceException("Fraud model service returned no response", null);
        }

        Transaction transaction = new Transaction(
                request.amount(), request.hour(), request.distanceFromHomeKm(),
                request.foreignTransaction(), request.onlineTransaction(),
                request.transactionsLast24h(), prediction.fraudProbability(),
                prediction.fraud(), prediction.modelVersion()
        );
        return TransactionResponse.from(repository.save(transaction));
    }

    public List<TransactionResponse> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream().map(TransactionResponse::from).toList();
    }

    public TransactionResponse findById(Long id) {
        return repository.findById(id).map(TransactionResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction " + id + " was not found"));
    }
}
