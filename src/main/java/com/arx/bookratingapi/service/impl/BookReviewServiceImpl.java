package com.arx.bookratingapi.service.impl;

import com.arx.bookratingapi.exceptionhandler.customexceptions.NotFoundException;
import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.dto.DetailedBookReviewResponse;
import com.arx.bookratingapi.model.dto.PagedResults;
import com.arx.bookratingapi.model.dto.TopRatedBook;
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

        checkBookHasReviews(bookReviews, bookId);

        SingleBookResponse bookResponse = bookService.searchById(bookId);

        return BookReviewMapper.toDetailedBookReviewResponse(bookResponse, bookReviews, bookReviews.getFirst().getRating());
    }

  @Override
  public PagedResults<SingleBookResponse> getTopRatedBooks(Integer limit, Integer page) {
    List<TopRatedBook> topRatedBooks = bookReviewRepository.findTopRatedBooks(limit);

    List<Long> topRatedBookIds = topRatedBooks.stream().map(TopRatedBook::bookId).toList();

    return bookService.searchMultipleById(topRatedBookIds, page);
  }

  private void checkBookHasReviews(List<BookReview> bookReviews, Long bookId){
        if(bookReviews.isEmpty()){
            throw new NotFoundException("No reviews were found for the book with the given ID:" + bookId);
        }
    }

}
