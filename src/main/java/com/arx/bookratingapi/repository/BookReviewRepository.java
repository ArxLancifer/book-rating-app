package com.arx.bookratingapi.repository;

import com.arx.bookratingapi.model.dao.MonthlyBookRating;
import com.arx.bookratingapi.model.dao.TopRatedBook;
import com.arx.bookratingapi.model.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

  @Query(
      value = """
          SELECT 
              br.id,
              br.book_id, 
              br.review, 
              br.created_on,
              br.is_deleted,
              br.updated_on,
              ROUND(AVG(rating) OVER (PARTITION BY book_id)::NUMERIC, 2) AS rating 
          FROM book_review br
          WHERE book_id = :bookId
          """,
      nativeQuery = true)
  List<BookReview> findBookReviewsByBookIdAndAvgRate(Long bookId);


  @Query(
      value = """
              SELECT
                 book_id,
                 ROUND(AVG(rating)::NUMERIC, 2)::REAL AS average_rating,
                 COUNT(*) AS total_reviews
             FROM book_reviews.book_review br
             GROUP BY br.book_id
             ORDER BY average_rating DESC, total_reviews DESC
             LIMIT :limit;
          """,
      nativeQuery = true)
  List<TopRatedBook> findTopRatedBooks(Integer limit);

  @Query(
      value = """
              SELECT
                 EXTRACT(year from created_on)::INTEGER AS year,
                 EXTRACT(month from created_on)::INTEGER AS month,
                 (ROUND(AVG(rating), 2)::NUMERIC)::REAL AS average_rating
              FROM book_review br where book_id = :bookId
              GROUP BY year, month, book_id
              ORDER BY year, month
          """,
      nativeQuery = true)
  List<MonthlyBookRating> findMonthlyBookRatings(Long bookId);

}
