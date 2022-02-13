package wg.reactive.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import wg.model.Comment;
import wg.model.CommentEntity;
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

    @GetMapping("/db/comments")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CommentEntity> getAllCommentEntities() {
        return service.getAllCommentEntities();
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
}
