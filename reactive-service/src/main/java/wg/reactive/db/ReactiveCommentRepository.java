package wg.reactive.db;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import wg.model.CommentEntity;

@Repository
public interface ReactiveCommentRepository extends ReactiveMongoRepository<CommentEntity, Integer> {
}
