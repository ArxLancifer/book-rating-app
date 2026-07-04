package com.arx.bookratingapi.service;

import com.arx.bookratingapi.config.GutendexUrlProperties;
import com.arx.bookratingapi.model.dto.GutendexBookResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BookService {

    private final RestClient gutendexClient;
    private final GutendexUrlProperties gutendexUrlProperties;

    public BookService(RestClient gutendexClient, GutendexUrlProperties gutendexUrlProperties) {
        this.gutendexClient = gutendexClient;
        this.gutendexUrlProperties = gutendexUrlProperties;
    }

    public GutendexBookResponse searchBooksByTitle(String title) {

        GutendexBookResponse gutendexBookResponse = gutendexClient.get().uri(uriBuilder -> uriBuilder
                .path(gutendexUrlProperties.getBooksPath())
                .queryParam(gutendexUrlProperties.getSearchParam(), title)
                .build())
                .retrieve()
                .body(GutendexBookResponse.class);

        return gutendexBookResponse;
    }
}
