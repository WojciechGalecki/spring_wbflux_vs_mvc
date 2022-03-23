package wg.model;

import lombok.Data;

@Data
public class CommentEntity {

    private Integer postId;
    private String name;
    private String email;
    private String body;
}
