package dhicc.tpps.security.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    //TODO :: properties로 변경
    private static final long CONNECTION_TIMEOUT = 10000L;
    private static final long READ_TIMEOUT = 10000L;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT))
                .setReadTimeout(Duration.ofMillis(READ_TIMEOUT))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
