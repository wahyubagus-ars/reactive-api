package com.ars.reactiveapi.handler;

import com.ars.reactiveapi.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TransactionHandler {
    private final TransactionService transactionService;

    public TransactionHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Mono<ServerResponse> getTransactionData(ServerRequest request) {
        return transactionService.getTransactionData(request);
    }
}
