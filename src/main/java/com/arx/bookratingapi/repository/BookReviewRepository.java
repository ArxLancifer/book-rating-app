package com.arx.bookratingapi.repository;

import com.arx.bookratingapi.model.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

}
