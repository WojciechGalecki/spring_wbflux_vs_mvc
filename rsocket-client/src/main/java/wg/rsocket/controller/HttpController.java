package wg.rsocket.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import wg.model.CommentTable;
import wg.rsocket.client.ReactiveClient;

@RestController
@AllArgsConstructor
@Slf4j
public class HttpController {

    private final ReactiveClient client;

    @GetMapping(value = "/http/comments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CommentTable> getCommentStreamOverHttp() {
        log.info("Fetching comments via HTTP");
        return client.getComments();
    }
}
