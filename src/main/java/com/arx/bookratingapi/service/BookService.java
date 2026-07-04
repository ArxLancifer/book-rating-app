package com.arx.bookratingapi.service;

import com.arx.bookratingapi.model.dto.GutendexBookResponse;

public interface BookService {

    GutendexBookResponse searchByTitle(String title);

    GutendexBookResponse.GutendexBook searchById(Long bookId);

}
