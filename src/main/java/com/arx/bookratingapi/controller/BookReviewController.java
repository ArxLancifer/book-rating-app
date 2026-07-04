package com.arx.bookratingapi.controller;

import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.service.BookReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.arx.bookratingapi.shared.BookEndpoints.REVIEWS;

@RestController
@RequestMapping(REVIEWS)
public class BookReviewController {

    private final BookReviewService bookReviewService;

    public BookReviewController(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> postReview(@RequestBody @Valid BookReviewCommand bookReviewCommand){
        bookReviewService.postReview(bookReviewCommand);
        return null;
    }

}
