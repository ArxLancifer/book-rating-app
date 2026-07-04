package com.arx.bookratingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import static com.arx.bookratingapi.shared.GutendexUriConstants.CLIENT_BASE_URL;

@Configuration
public class GutendexRestClient {

    @Bean
    RestClient gutendexClient() {
        return RestClient.builder()
                .baseUrl(CLIENT_BASE_URL)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
