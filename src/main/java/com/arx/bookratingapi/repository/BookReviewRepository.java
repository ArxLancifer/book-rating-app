package com.arx.bookratingapi.repository;

import com.arx.bookratingapi.model.dto.TopRatedBook;
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

}
