package wg.blocking.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wg.model.CommentDocument;

@Repository
public interface BlockingCommentRepository extends CrudRepository<CommentDocument, Integer> {

    Page<CommentDocument> findAllByIdNotNull(Pageable pageable);
}
