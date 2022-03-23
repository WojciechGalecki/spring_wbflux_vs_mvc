package wg.model;

import org.springframework.beans.BeanUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentMapper {

//    public static CommentDocument toDocument(Comment comment) {
//        CommentDocument commentDocument = new CommentDocument();
//        BeanUtils.copyProperties(comment, commentDocument);
//        return commentDocument;
//    }
//
//    public static Comment fromDocument(CommentDocument commentDocument) {
//        return new Comment(
//            commentDocument.getId(),
//            commentDocument.getPostId(),
//            commentDocument.getName(),
//            commentDocument.getEmail(),
//            commentDocument.getBody());
//    }

    public static CommentTable toTable(Comment comment) {
        CommentTable commentTable = new CommentTable();
        BeanUtils.copyProperties(comment, commentTable);
        return commentTable;
    }

    public static Comment fromTable(CommentTable commentTable) {
        return new Comment(
            commentTable.getId(),
            commentTable.getPostId(),
            commentTable.getName(),
            commentTable.getEmail(),
            commentTable.getBody());
    }
}