package com.arx.bookratingapi.model.mapper;

import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.entity.BookReview;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookReviewMapper {

    public static BookReview toEntity(BookReviewCommand bookReviewCommand) {
        return new BookReview(
                bookReviewCommand.bookId(),
                bookReviewCommand.rating(),
                bookReviewCommand.review()
        );
    }

}
