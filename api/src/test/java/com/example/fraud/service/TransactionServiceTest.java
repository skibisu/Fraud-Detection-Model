package com.example.fraud.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.fraud.exception.ResourceNotFoundException;
import com.example.fraud.repository.TransactionRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class TransactionServiceTest {
    @Test
    void missingTransactionThrowsNotFound() {
        TransactionRepository repository = mock(TransactionRepository.class);
        when(repository.findById(99L)).thenReturn(Optional.empty());
        TransactionService service = new TransactionService(repository, mock(RestClient.class));

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }
}

