package com.arx.bookratingapi.service;

import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.dto.DetailedBookReviewResponse;

public interface BookReviewService {

    void postReview(BookReviewCommand bookReviewCommand);

    DetailedBookReviewResponse getDetailedBookReview(Long bookId);

}
