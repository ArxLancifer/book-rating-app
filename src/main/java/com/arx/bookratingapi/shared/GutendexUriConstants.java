package com.arx.bookratingapi.shared;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GutendexUriConstants {


        public static final String CLIENT_BASE_URL = "https://gutendex.com";
        public static final String SEARCH_BOOKS_PATH = "books/";
        public static final String SEARCH_BOOK_BY_ID_PATH = "books/{id}/";

        public static final String SEARCH_PARAM = "search";
        public static final String PAGE_PARAM = "page";
        public static final String IDS_PARAM = "ids";

}
