package wg.rsocket.controller;

import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/rsocket")
public class RsocketController {

    private final RSocketRequester rSocketRequester;

    @GetMapping("/request-response")
    public Mono<String> requestResponse() {
        return rSocketRequester
            .route("requestResponse")
            .retrieveMono(String.class)
            .log();
    }

    @GetMapping(value = "request-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> requestStream() {
        return rSocketRequester
            .route("requestStream")
            .retrieveFlux(String.class)
            .log();
    }
}
