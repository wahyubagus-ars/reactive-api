package com.ars.reactiveapi.service;

import com.ars.reactiveapi.dto.SubmitTransactionReq;
import com.ars.reactiveapi.entity.Transaction;
import com.ars.reactiveapi.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Flux<Transaction> getTransactionData() {
        return transactionRepository.findAll()
                .limitRate(5);
    }


    public Mono<Transaction> submitTransaction(Mono<SubmitTransactionReq> request) {
        return request.flatMap(r -> {
            Transaction transaction = Transaction.builder()
                    .transactionDate(LocalDateTime.now())
                    .userId(r.getUserId())
                    .status("COMPLETED")
                    .paymentMethod(r.getPaymentMethod())
                    .build();

            return transactionRepository.save(transaction);
        });
    }

    public static class DataEntry {
        private final Transaction transaction;
        private final Instant timestamp;

        public DataEntry(Transaction transaction, Instant timestamp) {
            this.transaction = transaction;
            this.timestamp = timestamp;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public Instant getTimestamp() {
            return timestamp;
        }
    }
}
