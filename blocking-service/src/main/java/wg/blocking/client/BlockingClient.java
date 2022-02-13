package wg.blocking.client;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import wg.model.Comment;

@Component
public class BlockingClient {

    private final RestTemplate restTemplate;

    public BlockingClient(@Value("${external-service.url}") String url) {
        this.restTemplate = new RestTemplateBuilder().rootUri(url).build();
    }

    public List<Comment> getComments() {
        return Arrays.asList(requireNonNull(restTemplate.getForObject("/comments", Comment[].class)));
    }
}
