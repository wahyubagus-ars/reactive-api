package com.ars.reactiveapi.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
public class HelloWorldHandler {

    public Mono<ServerResponse> helloWorld(ServerRequest request) {
        log.info("helloworld 1: {}", Thread.currentThread().getName());
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello World!"));
    }

    public Mono<ServerResponse> helloWorld2(ServerRequest request) {
        log.info("helloworld 2: {}", Thread.currentThread().getName());
        return Mono.just("Hello World 2!")
                .delayElement(Duration.ofSeconds(10))
                .flatMap(message -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(BodyInserters.fromObject(message))
                );
    }
}