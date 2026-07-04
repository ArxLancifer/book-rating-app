package com.arx.bookratingapi.service;

import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.dto.GutendexBookResponse;
import com.arx.bookratingapi.repository.BookReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class BookReviewService {

    private final BookService bookService;
    private final BookReviewRepository bookReviewRepository;

    public BookReviewService(BookService bookService, BookReviewRepository bookReviewRepository) {
        this.bookService = bookService;
        this.bookReviewRepository = bookReviewRepository;
    }

    public void postReview(BookReviewCommand bookReviewCommand){
        bookService.searchBookById(bookReviewCommand.bookId());
    }

}
