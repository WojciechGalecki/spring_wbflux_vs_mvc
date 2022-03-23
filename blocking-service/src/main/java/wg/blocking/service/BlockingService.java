package wg.blocking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import wg.blocking.client.BlockingClient;
import wg.blocking.db.BlockingCommentRepository;
import wg.model.Comment;
import wg.model.CommentDocument;
import wg.model.CommentMapper;

@Service
@AllArgsConstructor
public class BlockingService {

    private final BlockingClient client;
    private final BlockingCommentRepository commentRepository;

    public List<Comment> getComments() {
        return client.getComments();
    }

    public Iterable<CommentDocument> getAllCommentEntities() {
        return commentRepository.findAll();
    }

    public Page<CommentDocument> getCommentEntities(int page, int size) {
        return commentRepository.findAllByIdNotNull(PageRequest.of(page, size));
    }

    public Optional<CommentDocument> getById(Integer id) {
        return commentRepository.findById(id);
    }

    public void saveComments() {
        List<CommentDocument> commentEntities = getComments().stream()
            .map(CommentMapper::toDocument)
            .toList();

        commentRepository.saveAll(commentEntities);
    }
}
