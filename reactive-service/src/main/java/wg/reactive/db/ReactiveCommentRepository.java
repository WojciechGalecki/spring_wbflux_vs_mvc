package wg.reactive.db;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import wg.model.CommentDocument;

@Repository
public interface ReactiveCommentRepository extends ReactiveMongoRepository<CommentDocument, Integer> {

    Flux<CommentDocument> findAllByIdNotNull(Pageable pageable);
}
