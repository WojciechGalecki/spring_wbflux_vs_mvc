package wg.reactive.client;

import java.time.Duration;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import wg.model.Comment;
import wg.reactive.exception.CustomException;

@Component
@Slf4j
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

    public Flux<Comment> getCommentsWithRetry() {
        return webClient.get()
            .uri("/comments")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Comment.class)
            .doOnError(e -> log.error("An error occurred"))
            .retry(3)
            .log();
    }

    public Flux<Comment> getCommentsWithRetryWhen400() {
        return webClient.get()
            .uri("/comment")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, transformToCustomException())
            .bodyToFlux(Comment.class)
            .doOnError(e -> log.error("An error occurred"))
            .retryWhen(retrySpecification())
            .log();
    }

    private Function<ClientResponse, Mono<? extends Throwable>> transformToCustomException() {
        return response -> response.createException().map(CustomException::new);
    }

    private RetryBackoffSpec retrySpecification() {
        return Retry
            .fixedDelay(3, Duration.ofSeconds(1))
            .filter(e -> e instanceof CustomException)
            .doAfterRetry(signal -> log.error(signal.toString()))
            .onRetryExhaustedThrow((spec, signal) -> {
                log.error("Retry exhausted, total reties: {}", signal.totalRetries());
                return signal.failure();
            })
            .scheduler(Schedulers.newParallel("MY-NEW-THREAD"));
    }
}
