package wg.rsocket.controller;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class RsocketController {

    @MessageMapping("requestResponse")
    public Mono<String> requestResponse() {
        return Mono.just("response");
    }

    @MessageMapping("requestStream")
    public Flux<String> requestStream() {
        return Flux
            .fromStream(Stream.generate(() -> "stream response"))
            .delayElements(Duration.ofMillis(200));
    }
}
