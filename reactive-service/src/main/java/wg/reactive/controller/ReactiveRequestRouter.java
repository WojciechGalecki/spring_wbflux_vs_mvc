package wg.reactive.controller;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import wg.model.Comment;
import wg.reactive.service.ReactiveService;

@Configuration
public class ReactiveRequestRouter {

    @Autowired
    private ReactiveService service;

    @Bean
    RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(GET("/route/comments"), handleFunction);
    }

    HandlerFunction<ServerResponse> handleFunction = (request -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(service.getComments(), Comment.class));
}
