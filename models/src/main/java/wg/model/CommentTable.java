package wg.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Data
@Table(value = "comments")
public class CommentTable extends CommentEntity {

    @PrimaryKey
    private Integer id;
}
