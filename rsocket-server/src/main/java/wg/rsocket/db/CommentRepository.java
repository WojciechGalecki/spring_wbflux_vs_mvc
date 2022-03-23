package wg.rsocket.db;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import wg.model.CommentTable;

@Repository
public interface CommentRepository extends ReactiveCassandraRepository<CommentTable, Integer> {
}
