package wg.reactive.controller;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wg.model.Comment;
import wg.model.CommentDocument;
import wg.reactive.service.ReactiveService;

@RestController
@AllArgsConstructor
public class ReactiveController {

    private final ReactiveService service;

    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Comment> getComments() {
        return service.getComments();
    }

    @GetMapping(value = "/db/comments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<CommentDocument> getAllCommentEntities() {
        return service.getAllCommentEntities();
    }

    @GetMapping("/db/comments/page")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CommentDocument> getAllCommentEntities(@RequestParam int page, @RequestParam int size) {
        return service.getCommentEntities(page, size);
    }

    @GetMapping("/db/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CommentDocument> getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveComments() {
        service.saveComments();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getStream() {
        return service.getInfiniteStream();
    }

    @GetMapping(value = "/stream/zip", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> zip() {
        Flux<Integer> p1 = Flux.fromStream(Stream.generate(() -> 1));
        Flux<Integer> p2 = Flux.fromStream(Stream.generate(() -> 2));

        return p1.zipWith(p2, Integer::sum)
            .delayElements(Duration.ofMillis(200))
            //.limitRate(3) // backpressure - constantly request only 3 instead of unbound values
            .log();
    }
}
