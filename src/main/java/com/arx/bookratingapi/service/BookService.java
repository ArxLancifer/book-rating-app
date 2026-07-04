package com.arx.bookratingapi.service;

import com.arx.bookratingapi.model.dto.PagedResults;
import com.arx.bookratingapi.model.dto.gutendex.SingleBookResponse;

public interface BookService {

    PagedResults<SingleBookResponse> searchByTitle(String title, Integer page);

    SingleBookResponse searchById(Long bookId);

}
