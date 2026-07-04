package com.arx.bookratingapi.service;

import com.arx.bookratingapi.exceptionhandler.customexceptions.NotFoundException;
import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.dto.DetailedBookReviewResponse;
import com.arx.bookratingapi.model.dto.GutendexBookResponse;
import com.arx.bookratingapi.model.entity.BookReview;
import com.arx.bookratingapi.model.mapper.BookReviewMapper;
import com.arx.bookratingapi.repository.BookReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewService {

    private final BookService bookService;
    private final BookReviewRepository bookReviewRepository;

    public BookReviewService(BookService bookService, BookReviewRepository bookReviewRepository) {
        this.bookService = bookService;
        this.bookReviewRepository = bookReviewRepository;
    }

    @Transactional
    public void postReview(BookReviewCommand bookReviewCommand){

        bookService.searchBookById(bookReviewCommand.bookId());

        BookReview bookReview = BookReviewMapper.toEntity(bookReviewCommand);

        bookReviewRepository.save(bookReview);
    }

    public DetailedBookReviewResponse getDetailedBookReview(Long bookId){

        List<BookReview> bookReviews = bookReviewRepository.getBookReviewsByBookIdAndAvgRate(bookId);

        checkBookHasReviews(bookReviews, bookId);

        GutendexBookResponse.GutendexBook bookResponse = bookService.searchBookById(bookId);

        return BookReviewMapper.toDetailedBookReviewResponse(bookResponse, bookReviews, bookReviews.getFirst().getRating());
    }

    private void checkBookHasReviews(List<BookReview> bookReviews, Long bookId){
        if(bookReviews.isEmpty()){
            throw new NotFoundException("No reviews were found for the book with the given ID:" + bookId);
        }
    }

}
