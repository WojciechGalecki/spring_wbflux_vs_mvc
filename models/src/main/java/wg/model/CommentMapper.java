package wg.model;

import org.springframework.beans.BeanUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentMapper {

    public static CommentEntity toEntity(Comment comment) {
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(comment, commentEntity);
        return commentEntity;
    }

    public static Comment fromEntity(CommentEntity commentEntity) {
        return new Comment(
            commentEntity.getId(),
            commentEntity.getPostId(),
            commentEntity.getName(),
            commentEntity.getEmail(),
            commentEntity.getBody());
    }
}
