package wg.model;

public record Comment(Integer id, Integer postId, String name, String email, String body) {
}
