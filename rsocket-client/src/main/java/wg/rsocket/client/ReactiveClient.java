package wg.rsocket.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import wg.model.CommentTable;

@Component
public class ReactiveClient {

    private final WebClient webClient;

    public ReactiveClient() {
        this.webClient = WebClient.create("http://localhost:8080");
    }

    public Flux<CommentTable> getComments() {
        return webClient.get()
            .uri("/comments")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .exchangeToFlux(response -> response.bodyToFlux(CommentTable.class));
    }
}
