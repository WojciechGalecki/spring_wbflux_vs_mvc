package wg.http.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.oss.driver.api.core.CqlSession;

@Configuration
public class CassandraConfiguration {
    private static final String DATA_CENTER = "datacenter1";
    private static final String KEY_SPACE = "demo";

    @Bean
    public CqlSession session() {
        return CqlSession.builder()
            .withLocalDatacenter(DATA_CENTER)
            .withKeyspace(KEY_SPACE)
            .build();
    }
}