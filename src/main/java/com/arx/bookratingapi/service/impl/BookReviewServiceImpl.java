package com.arx.bookratingapi.service.impl;

import com.arx.bookratingapi.exceptionhandler.customexceptions.NotFoundException;
import com.arx.bookratingapi.model.dao.MonthlyBookRating;
import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.dto.DetailedBookReviewResponse;
import com.arx.bookratingapi.model.dto.MonthlyBookRatingResponse;
import com.arx.bookratingapi.model.dto.PagedResults;
import com.arx.bookratingapi.model.dao.TopRatedBook;
import com.arx.bookratingapi.model.dto.gutendex.SingleBookResponse;
import com.arx.bookratingapi.model.entity.BookReview;
import com.arx.bookratingapi.model.mapper.BookReviewMapper;
import com.arx.bookratingapi.repository.BookReviewRepository;
import com.arx.bookratingapi.service.BookReviewService;
import com.arx.bookratingapi.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService {

    private final BookService bookService;
    private final BookReviewRepository bookReviewRepository;

  public BookReviewServiceImpl(BookService bookService, BookReviewRepository bookReviewRepository) {
    this.bookService = bookService;
    this.bookReviewRepository = bookReviewRepository;
  }


  @Transactional
    public void postReview(BookReviewCommand bookReviewCommand){

        bookService.searchById(bookReviewCommand.bookId());

        BookReview bookReview = BookReviewMapper.toEntity(bookReviewCommand);

        bookReviewRepository.save(bookReview);
    }

    public DetailedBookReviewResponse findDetailedBookReview(Long bookId){

        List<BookReview> bookReviews = bookReviewRepository.findBookReviewsByBookIdAndAvgRate(bookId);

        handleEmptyList(bookReviews, bookId, "reviews");

        SingleBookResponse bookResponse = bookService.searchById(bookId);

        return BookReviewMapper.toDetailedBookReviewResponse(bookResponse, bookReviews, bookReviews.getFirst().getRating());
    }

  @Override
  public PagedResults<SingleBookResponse> getTopRatedBooks(Integer limit, Integer page) {
    List<TopRatedBook> topRatedBooks = bookReviewRepository.findTopRatedBooks(limit);

    List<Long> topRatedBookIds = topRatedBooks.stream().map(TopRatedBook::bookId).toList();

    return bookService.searchMultipleById(topRatedBookIds, page);
  }

  @Override
  public MonthlyBookRatingResponse getMonthlyBookRatings(Long bookId) {

    List<MonthlyBookRating> monthlyBookRatings = bookReviewRepository.findMonthlyBookRatings(bookId);

    handleEmptyList(monthlyBookRatings, bookId, "ratings");

    SingleBookResponse singleBookResponse = bookService.searchById(bookId);

    return new MonthlyBookRatingResponse(singleBookResponse, monthlyBookRatings);
  }

  private <T> void handleEmptyList(List<T> listElements, Long bookId, String subject){
        if(listElements.isEmpty()){
            throw new NotFoundException(String.format("No %s were found for given book ID: %d", subject, bookId));
        }
    }

}
