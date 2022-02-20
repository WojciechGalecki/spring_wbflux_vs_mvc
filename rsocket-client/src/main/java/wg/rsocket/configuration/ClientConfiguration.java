package wg.rsocket.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;

import reactor.util.retry.Retry;

@Configuration
public class ClientConfiguration {
    private static final long MAX_RETRY_ATTEMPTS = 3L;
    private static final long RETRY_DELAY_MS = 300L;

    @Bean
    public RSocketRequester rSocketRequester(
        @Value("${rsocket-server.host}") String serverHost,
        @Value("${rsocket-server.port}") int serverPort) {

        return RSocketRequester.builder()
            .rsocketConnector(connector -> connector.reconnect(Retry.fixedDelay(MAX_RETRY_ATTEMPTS, Duration.ofMillis(RETRY_DELAY_MS))))
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            //.dataMimeType(MimeTypeUtils.APPLICATION_OCTET_STREAM)
            .tcp(serverHost, serverPort);
    }
}
