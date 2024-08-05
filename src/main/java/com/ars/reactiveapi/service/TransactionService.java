package com.ars.reactiveapi.service;

import com.ars.reactiveapi.entity.Transaction;
import com.ars.reactiveapi.repository.TransactionRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Flux<Transaction> getTransactionData(ServerRequest request) {
        return transactionRepository.findAll();
    }

    public Mono<Transaction> submitTransaction(ServerRequest request) {

        Transaction transaction = Transaction.builder()
                .transactionDate(LocalDateTime.now())
                .userId(1L)
                .status("COMPLETED")
                .paymentMethod("QRIS")
                .build();
        return transactionRepository.save(transaction)
                .delayElement(Duration.ofSeconds(5));
    }
}
