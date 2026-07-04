package com.arx.bookratingapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.gutendex-client")
@Getter
@Setter
public class GutendexUrlProperties {

    private String baseUrl;
    private String searchBooksPath;
    private String searchBookByIdPath;

    private String searchParam;

}
