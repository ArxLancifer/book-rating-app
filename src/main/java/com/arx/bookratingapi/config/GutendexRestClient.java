package com.arx.bookratingapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class GutendexRestClient {

    @Value("${gutendex-base-url}")
    private String baseUrl;

    @Bean
    RestClient gutendexClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
