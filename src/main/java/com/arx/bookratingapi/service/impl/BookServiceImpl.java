package com.arx.bookratingapi.service.impl;

import static com.arx.bookratingapi.shared.GutendexUriConstants.PAGE_PARAM;
import static com.arx.bookratingapi.shared.GutendexUriConstants.SEARCH_BOOKS_PATH;
import static com.arx.bookratingapi.shared.GutendexUriConstants.SEARCH_BOOK_BY_ID_PATH;
import static com.arx.bookratingapi.shared.GutendexUriConstants.SEARCH_PARAM;

import com.arx.bookratingapi.model.dto.PagedResults;
import com.arx.bookratingapi.model.dto.gutendex.GutendexBookResponse;
import com.arx.bookratingapi.model.dto.gutendex.SingleBookResponse;
import com.arx.bookratingapi.service.BookService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class BookServiceImpl implements BookService {

    private final RestClient gutendexClient;

    public BookServiceImpl(RestClient gutendexClient) {
        this.gutendexClient = gutendexClient;
    }

    @Cacheable(value = "books", key = "#title + '-' + #page")
    public PagedResults<SingleBookResponse> searchByTitle(String title, Integer page) {

        GutendexBookResponse gutendexBookResponse = gutendexClient.get().uri(uriBuilder -> uriBuilder
                        .path(SEARCH_BOOKS_PATH)
                        .queryParam(SEARCH_PARAM, title)
                        .queryParam(PAGE_PARAM, page)
                        .build())
                .retrieve()
                .body(GutendexBookResponse.class);



        return buildPagedResult(gutendexBookResponse, page);

    }

    @Cacheable(value = "books", key = "#bookId")
    public SingleBookResponse searchById(Long bookId){

       return gutendexClient.get().uri(uriBuilder -> uriBuilder
                .path(SEARCH_BOOK_BY_ID_PATH)
                .build(bookId))
                .retrieve()
                .body(SingleBookResponse.class);
    }

    private PagedResults<SingleBookResponse> buildPagedResult(GutendexBookResponse gutendexBookResponse, Integer currentPage){

        if(gutendexBookResponse == null) {
            throw new IllegalStateException("Something went wrong, Gutendex API responded with null");
        }

        String nextPageUri = null;

        String previousPageUri = null;

        if(StringUtils.hasText(gutendexBookResponse.next())){
            nextPageUri = ServletUriComponentsBuilder.
                    fromCurrentRequest()
                    .replaceQueryParam("page", currentPage + 1)
                    .toUriString();

        }

        if(StringUtils.hasText(gutendexBookResponse.previous())){
            previousPageUri = ServletUriComponentsBuilder.
                    fromCurrentRequest()
                    .replaceQueryParam("page", currentPage - 1)
                    .toUriString();
            }


        return PagedResults.of(
                gutendexBookResponse.count(),
                nextPageUri,
                previousPageUri,
                gutendexBookResponse.results()
        );
    }
}
