package com.arx.bookratingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class GutendexRestClient {

    private final GutendexUrlProperties gutendexUrlProperties;

    public GutendexRestClient(GutendexUrlProperties gutendexUrlProperties) {
        this.gutendexUrlProperties = gutendexUrlProperties;
    }

    @Bean
    RestClient gutendexClient() {
        return RestClient.builder()
                .baseUrl(gutendexUrlProperties.getBaseUrl())
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
