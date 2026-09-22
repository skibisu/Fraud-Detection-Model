package com.example.fraud.controller;

import com.example.fraud.dto.TransactionRequest;
import com.example.fraud.dto.TransactionResponse;
import com.example.fraud.service.TransactionService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) { this.service = service; }

    @PostMapping("/predict")
    ResponseEntity<TransactionResponse> predict(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse saved = service.predictAndSave(request);
        return ResponseEntity.created(URI.create("/api/transactions/" + saved.id())).body(saved);
    }

    @GetMapping
    List<TransactionResponse> all() { return service.findAll(); }

    @GetMapping("/{id}")
    TransactionResponse one(@PathVariable Long id) { return service.findById(id); }
}

