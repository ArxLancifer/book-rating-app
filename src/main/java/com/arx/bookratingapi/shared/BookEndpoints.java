package com.arx.bookratingapi.shared;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookEndpoints {

    public static final String BOOKS = "/books";
    public static final String BOOKS_SEARCH = "/search";
    public static final String REVIEWS = "/reviews";

}
