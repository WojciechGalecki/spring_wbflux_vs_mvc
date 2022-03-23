package wg.rsocket.controller;

import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import wg.model.CommentTable;

@RestController
@AllArgsConstructor
@Slf4j
public class RsocketController {

    private final RSocketRequester rSocketRequester;

    @GetMapping(value = "/rsocket/comments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CommentTable> getCommentStreamOverRsocket() {
        log.info("Fetching comments via RSocket");
        return rSocketRequester
            .route("comments")
            .retrieveFlux(CommentTable.class);
    }
}
