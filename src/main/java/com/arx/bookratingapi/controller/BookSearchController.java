package com.arx.bookratingapi.controller;

import com.arx.bookratingapi.model.dto.PagedResults;
import com.arx.bookratingapi.model.dto.gutendex.SingleBookResponse;
import com.arx.bookratingapi.service.BookService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<PagedResults<SingleBookResponse>> searchByTitle(@RequestParam String title, @RequestParam(defaultValue = "1") Integer page) {
        PagedResults<SingleBookResponse> response = bookService.searchByTitle(title, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
