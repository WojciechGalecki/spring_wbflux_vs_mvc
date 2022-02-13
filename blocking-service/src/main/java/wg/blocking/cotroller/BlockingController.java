package wg.blocking.cotroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import wg.blocking.service.BlockingService;
import wg.model.Comment;
import wg.model.CommentEntity;

@RestController
@AllArgsConstructor
public class BlockingController {

    private final BlockingService service;

    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getComments() {
        return service.getComments();
    }

    @GetMapping("/db/comments")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<CommentEntity> getAllCommentEntities() {
        return service.getAllCommentEntities();
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveComments() {
        service.saveComments();
    }
}
