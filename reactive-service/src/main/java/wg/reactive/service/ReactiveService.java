package wg.reactive.service;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import wg.model.Comment;
import wg.model.CommentEntity;
import wg.model.CommentMapper;
import wg.reactive.client.ReactiveClient;
import wg.reactive.db.ReactiveCommentRepository;

@Service
@AllArgsConstructor
public class ReactiveService {

    private final ReactiveClient client;
    private final ReactiveCommentRepository commentRepository;

    public Flux<Comment> getComments() {
        return client.getComments();
    }

    public Flux<CommentEntity> getAllCommentEntities() {
        return commentRepository.findAll();
    }

    public void saveComments() {
        Flux<CommentEntity> commentEntities = getComments().map(CommentMapper::toEntity);

        commentRepository.saveAll(commentEntities).subscribe();
    }

    public Flux<String> getInfiniteStream() {
        return Flux.fromStream(Stream.generate(() -> "VALUE"))
            .delayElements(Duration.ofSeconds(1))
            .log();
    }
}
