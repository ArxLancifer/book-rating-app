package com.arx.bookratingapi.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_review")
@NoArgsConstructor
@Getter
public class BookReview extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_review_seq")
    @SequenceGenerator(name = "book_review_seq", sequenceName = "book_review_seq", allocationSize = 1)
    Long id;

    @Column(name = "book_id", nullable = false)
    Long bookId;

    @Column(name = "rating", nullable = false, columnDefinition = "numeric(2,1)")
    Float rating;

    @Column(name = "review")
    String review;

    public BookReview(Long bookId, Float rating, String review){
        this.bookId = bookId;
        this.rating = rating;
        this.review = review;
    }

}
