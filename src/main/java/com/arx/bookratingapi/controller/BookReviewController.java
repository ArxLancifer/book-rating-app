package com.arx.bookratingapi.controller;

import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.dto.DetailedBookReviewResponse;
import com.arx.bookratingapi.model.dto.PagedResults;
import com.arx.bookratingapi.model.dto.gutendex.SingleBookResponse;
import com.arx.bookratingapi.service.BookReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.arx.bookratingapi.shared.BookEndpoints.REVIEWS;
import static com.arx.bookratingapi.shared.BookEndpoints.TOP_RATED;

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
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<DetailedBookReviewResponse> getDetailedBookReview(@PathVariable Long bookId) {
        DetailedBookReviewResponse detailedBookReviewResponse = bookReviewService.findDetailedBookReview(bookId);
        return ResponseEntity.ok(detailedBookReviewResponse);
    }

    @GetMapping(TOP_RATED)
    public ResponseEntity<PagedResults<SingleBookResponse>> getTopRatedBooks(@RequestParam Integer limit, @RequestParam(defaultValue = "1") Integer page){
        return ResponseEntity.ok(bookReviewService.getTopRatedBooks(limit, page));
    }

}
