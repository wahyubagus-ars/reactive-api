package com.ars.reactiveapi.router;

import com.ars.reactiveapi.handler.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TransactionRouter {

    @Bean
    public RouterFunction<ServerResponse> transactionRoute(TransactionHandler transactionHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/transaction")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), transactionHandler::getTransactionData)
                .andRoute(RequestPredicates.POST("/transaction/submit")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), transactionHandler::submitTransaction);
    }
}
