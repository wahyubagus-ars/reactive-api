package com.ars.reactiveapi.handler;

import com.ars.reactiveapi.dto.SubmitTransactionReq;
import com.ars.reactiveapi.entity.Transaction;
import com.ars.reactiveapi.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TransactionHandler {
    private final TransactionService transactionService;

    public TransactionHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public @NotNull Mono<ServerResponse> getTransactionData(ServerRequest request) {
        return ServerResponse.ok().body(transactionService.getTransactionData(), Transaction.class);
    }

    public @NotNull Mono<ServerResponse> submitTransaction(ServerRequest request) {
        Mono<SubmitTransactionReq> requestData = request.bodyToMono(SubmitTransactionReq.class);
        return ServerResponse.ok().body(transactionService.submitTransaction(requestData), Transaction.class);
    }
}
