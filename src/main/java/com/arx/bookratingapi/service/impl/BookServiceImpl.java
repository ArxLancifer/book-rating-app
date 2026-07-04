package com.arx.bookratingapi.service.impl;

import com.arx.bookratingapi.config.GutendexUrlProperties;
import com.arx.bookratingapi.model.dto.GutendexBookResponse;
import com.arx.bookratingapi.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BookServiceImpl implements BookService {

    private final RestClient gutendexClient;
    private final GutendexUrlProperties gutendexUrlProperties;

    public BookServiceImpl(RestClient gutendexClient, GutendexUrlProperties gutendexUrlProperties) {
        this.gutendexClient = gutendexClient;
        this.gutendexUrlProperties = gutendexUrlProperties;
    }

    public GutendexBookResponse searchByTitle(String title) {

        return gutendexClient.get().uri(uriBuilder -> uriBuilder
                .path(gutendexUrlProperties.getSearchBooksPath())
                .queryParam(gutendexUrlProperties.getSearchParam(), title)
                .build())
                .retrieve()
                .body(GutendexBookResponse.class);
    }

    public GutendexBookResponse.GutendexBook searchById(Long bookId){

       return gutendexClient.get().uri(uriBuilder -> uriBuilder
                .path(gutendexUrlProperties.getSearchBookByIdPath())
                .build(bookId))
                .retrieve()
                .body(GutendexBookResponse.GutendexBook.class);
    }
}
