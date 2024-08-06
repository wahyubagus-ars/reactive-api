package com.ars.reactiveapi.service;

import com.ars.reactiveapi.entity.Transaction;
import com.ars.reactiveapi.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

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

//    public Flux<DataEntry> getTransactionData() {
//        return transactionRepository.findAll()
//                .limitRate(5)
//                .doOnNext(item -> log.info("item processing :: {}", item))
//                .map(i -> new DataEntry(i, Instant.now()))
//                .window(5) // Group elements into windows of 5 elements each
//                .concatMap(window ->
//                        window
//                                .concatMap(Flux::just) // Flatten the window back into a Flux
//                                .delayElements(Duration.ZERO) // No delay between elements within the same group
//                                .collectList() // Collect the window into a List
//                                .flatMapMany(list ->
//                                        Flux.fromIterable(list) // Convert the List back to a Flux
//                                                .delaySubscription(Duration.ofSeconds(1)) // Delay the emission of the entire window
//                                )
//                )
//                .publishOn(Schedulers.boundedElastic());
//
//    }

//    public Flux<Transaction> getTransactionData() {
//        return transactionRepository.findAll()
//                .limitRate(5);
//    }

        public Flux<Transaction> getTransactionData() {
            return Flux.interval(Duration.ofSeconds(1)) // Simulates updates every second
                    .map(tick -> Transaction.builder().transactionId(1L).build())
                    .publishOn(Schedulers.boundedElastic());
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
