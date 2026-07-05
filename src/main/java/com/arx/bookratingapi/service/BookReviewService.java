package com.arx.bookratingapi.service;

import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.dto.DetailedBookReviewResponse;
import com.arx.bookratingapi.model.dto.PagedResults;
import com.arx.bookratingapi.model.dto.gutendex.SingleBookResponse;

public interface BookReviewService {

    void postReview(BookReviewCommand bookReviewCommand);

    DetailedBookReviewResponse findDetailedBookReview(Long bookId);

    PagedResults<SingleBookResponse> getTopRatedBooks(Integer limit, Integer page);
}
