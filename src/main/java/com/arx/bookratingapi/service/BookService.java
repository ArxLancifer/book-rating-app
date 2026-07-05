package com.arx.bookratingapi.service;

import com.arx.bookratingapi.model.dto.PagedResults;
import com.arx.bookratingapi.model.dto.gutendex.SingleBookResponse;
import java.util.List;

public interface BookService {

    PagedResults<SingleBookResponse> searchByTitle(String title, Integer page);

    SingleBookResponse searchById(Long bookId);

    PagedResults<SingleBookResponse> searchMultipleById(List<Long> bookIds, Integer page);
}
