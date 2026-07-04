package com.arx.bookratingapi.service.impl;

import com.arx.bookratingapi.exceptionhandler.customexceptions.NotFoundException;
import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.dto.DetailedBookReviewResponse;
import com.arx.bookratingapi.model.dto.GutendexBookResponse;
import com.arx.bookratingapi.model.entity.BookReview;
import com.arx.bookratingapi.model.mapper.BookReviewMapper;
import com.arx.bookratingapi.repository.BookReviewRepository;
import com.arx.bookratingapi.service.BookReviewService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService {

    private final BookServiceImpl bookServiceImpl;
    private final BookReviewRepository bookReviewRepository;

    public BookReviewServiceImpl(BookServiceImpl bookServiceImpl, BookReviewRepository bookReviewRepository) {
        this.bookServiceImpl = bookServiceImpl;
        this.bookReviewRepository = bookReviewRepository;
    }

    @Transactional
    public void postReview(BookReviewCommand bookReviewCommand){

        bookServiceImpl.searchById(bookReviewCommand.bookId());

        BookReview bookReview = BookReviewMapper.toEntity(bookReviewCommand);

        bookReviewRepository.save(bookReview);
    }

    public DetailedBookReviewResponse getDetailedBookReview(Long bookId){

        List<BookReview> bookReviews = bookReviewRepository.getBookReviewsByBookIdAndAvgRate(bookId);

        checkBookHasReviews(bookReviews, bookId);

        GutendexBookResponse.GutendexBook bookResponse = bookServiceImpl.searchById(bookId);

        return BookReviewMapper.toDetailedBookReviewResponse(bookResponse, bookReviews, bookReviews.getFirst().getRating());
    }

    private void checkBookHasReviews(List<BookReview> bookReviews, Long bookId){
        if(bookReviews.isEmpty()){
            throw new NotFoundException("No reviews were found for the book with the given ID:" + bookId);
        }
    }

}
