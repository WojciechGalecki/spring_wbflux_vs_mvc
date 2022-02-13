package wg.blocking.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wg.model.CommentEntity;

@Repository
public interface BlockingCommentRepository extends CrudRepository<CommentEntity, Integer> {
}
