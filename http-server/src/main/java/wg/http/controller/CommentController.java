package wg.http.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import wg.http.db.CommentRepository;
import wg.model.CommentTable;

@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentRepository repository;

    @GetMapping(value = "/comments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CommentTable> getAllComments() {
        return repository.findAll();
    }
}
