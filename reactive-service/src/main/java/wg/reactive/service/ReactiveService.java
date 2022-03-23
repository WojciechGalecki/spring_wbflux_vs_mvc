package wg.reactive.service;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wg.model.Comment;
import wg.model.CommentDocument;
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

    public Flux<CommentDocument> getAllCommentEntities() {
        return commentRepository.findAll();
    }

    public Flux<CommentDocument> getCommentEntities(int page, int size) {
        return commentRepository.findAllByIdNotNull(PageRequest.of(page, size));
    }

    public Mono<CommentDocument> getById(Integer id) {
        return commentRepository.findById(id);
    }

    public void saveComments() {
        Flux<CommentDocument> commentEntities = getComments().map(CommentMapper::toDocument);
        commentRepository.saveAll(commentEntities).subscribe();
    }

    public Flux<String> getInfiniteStream() {
        return Flux.fromStream(Stream.generate(() -> "VALUE"))
            .delayElements(Duration.ofSeconds(1))
            .log();
    }
}
