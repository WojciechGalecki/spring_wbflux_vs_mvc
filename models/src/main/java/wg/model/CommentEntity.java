package wg.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "comments")
public class CommentEntity {

    @Id
    private Integer id;
    private Integer postId;
    private String name;
    private String email;
    private String body;
}
