package wg.reactive.client;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wg.model.Comment;

@Component
public class ReactiveClient {

    private final WebClient webClient;

    public ReactiveClient(@Value("${external-service.url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public Flux<Comment> getComments() {
        return webClient.get()
            .uri("/comments")
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToFlux(responseHandler(Comment.class));
    }

    private <T> Function<ClientResponse, Flux<T>> responseHandler(Class<T> clazz) {
        return response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToFlux(clazz);
            } else {
                return response.createException().flatMapMany(Mono::error);
            }
        };
    }
}
