package com.arx.bookratingapi.controller;

import com.arx.bookratingapi.model.dto.GutendexBookResponse;
import com.arx.bookratingapi.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.arx.bookratingapi.shared.BookEndpoints.BOOKS;
import static com.arx.bookratingapi.shared.BookEndpoints.BOOKS_SEARCH;

@RestController
@RequestMapping(BOOKS)
public class BookSearchController {

    private final BookService bookService;

    public BookSearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(BOOKS_SEARCH)
    public ResponseEntity<GutendexBookResponse> searchByTitle(@RequestParam String title) {
        GutendexBookResponse response = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(response);
    }

}
