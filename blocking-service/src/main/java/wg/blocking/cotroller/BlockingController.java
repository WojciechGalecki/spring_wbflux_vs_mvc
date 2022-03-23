package wg.blocking.cotroller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import wg.blocking.service.BlockingService;
import wg.model.Comment;
import wg.model.CommentDocument;

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
    public Iterable<CommentDocument> getAllCommentEntities() {
        return service.getAllCommentEntities();
    }

    @GetMapping("/db/comments/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentDocument> getCommentEntities(@RequestParam int page, @RequestParam int size) {
        return service.getCommentEntities(page, size);
    }

    @GetMapping("/db/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CommentDocument> getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveComments() {
        service.saveComments();
    }
}
