package com.arx.bookratingapi.model.mapper;

import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.dto.DetailedBookReviewResponse;
import com.arx.bookratingapi.model.dto.GutendexBookResponse;
import com.arx.bookratingapi.model.entity.BookReview;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookReviewMapper {

    public static BookReview toEntity(BookReviewCommand bookReviewCommand) {
        return new BookReview(
                bookReviewCommand.bookId(),
                bookReviewCommand.rating(),
                bookReviewCommand.review()
        );
    }

    public static DetailedBookReviewResponse toDetailedBookReviewResponse(GutendexBookResponse.GutendexBook gutendexBook, List<BookReview> bookReviews, Float avgRating){

        List<String> reviews = bookReviews.stream().map(BookReview::getReview).filter(Objects::nonNull).toList();

        return new DetailedBookReviewResponse(
                gutendexBook.id(),
                gutendexBook.title(),
                gutendexBook.authors(),
                gutendexBook.languages(),
                gutendexBook.downloadCount(),
                avgRating,
                reviews
        );
    }

}
