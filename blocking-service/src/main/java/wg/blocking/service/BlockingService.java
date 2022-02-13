package wg.blocking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import wg.blocking.client.BlockingClient;
import wg.blocking.db.BlockingCommentRepository;
import wg.model.Comment;
import wg.model.CommentEntity;
import wg.model.CommentMapper;

@Service
@AllArgsConstructor
public class BlockingService {

    private final BlockingClient client;
    private final BlockingCommentRepository commentRepository;

    public List<Comment> getComments() {
        return client.getComments();
    }

    public Iterable<CommentEntity> getAllCommentEntities() {
        return commentRepository.findAll();
    }

    public void saveComments() {
        List<CommentEntity> commentEntities = getComments().stream()
            .map(CommentMapper::toEntity)
            .toList();

        commentRepository.saveAll(commentEntities);
    }
}
