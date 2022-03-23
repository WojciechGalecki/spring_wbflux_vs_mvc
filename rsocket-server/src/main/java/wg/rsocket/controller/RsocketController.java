package wg.rsocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import wg.model.CommentTable;
import wg.rsocket.db.CommentRepository;

@Controller
@AllArgsConstructor
public class RsocketController {

    private final CommentRepository repository;

    @MessageMapping("comments")
    public Flux<CommentTable> getAllComments() {
        return repository.findAll();
    }
}
