package com.ars.reactiveapi.router;

import com.ars.reactiveapi.handler.HelloWorldHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HelloWorldRouter {

    @Bean
    public RouterFunction<ServerResponse> routeHelloWorld(HelloWorldHandler helloWorldHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/helloworld").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), helloWorldHandler::helloWorld)
                .andRoute(RequestPredicates.GET("/helloworld2").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), helloWorldHandler::helloWorld2);
    }
}