package wg.reactive.client;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import wg.reactive.exception.CustomException;

@Component
@Slf4j
public class WebClientWrapper {
    private static final long MAX_RETRY_ATTEMPTS = 3L;
    private static final long RETRY_DELAY_MS = 3L;

    private final WebClient webClient;

    public WebClientWrapper(@Value("${external-service.url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public<T> Flux<T> exchange(HttpMethod httpMethod, String path, Consumer<HttpHeaders> headersConsumer, Object body, Class<T> responseType) {
        return webClient
            .method(httpMethod)
            .uri(path)
            .headers(headersConsumer)
            .bodyValue(body)
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError, transformToCustomException())
            .bodyToFlux(responseType)
            .doOnError(e -> log.error("An error occurred"))
            .retryWhen(retrySpecification())
            .log();
    }

    private Function<ClientResponse, Mono<? extends Throwable>> transformToCustomException() {
        return response -> response.createException().map(CustomException::new);
    }

    private RetryBackoffSpec retrySpecification() {
        return Retry
            .fixedDelay(MAX_RETRY_ATTEMPTS, Duration.ofMillis(RETRY_DELAY_MS))
            .filter(e -> e instanceof CustomException)
            .doAfterRetry(signal -> log.error(signal.toString()))
            .onRetryExhaustedThrow((spec, signal) -> {
                log.error("Retry exhausted, total reties: {}", signal.totalRetries());
                return signal.failure();
            });
    }
}
