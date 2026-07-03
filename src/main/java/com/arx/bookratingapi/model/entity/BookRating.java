package com.arx.bookratingapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_rating")
@NoArgsConstructor
@Getter
public class BookRating extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_rating_seq")
    @SequenceGenerator(name = "book_rating_seq", sequenceName = "book_rating_seq", allocationSize = 1)
    Long id;

    @Column(name = "book_id", nullable = false)
    Long bookId;

    @Column(name = "rating", nullable = false, columnDefinition = "numeric(2,1)")
    Float rating;

    @Column(name = "review")
    String review;

    public BookRating(Long bookId, Float rating, String review){
        this.bookId = bookId;
        this.rating = rating;
        this.review = review;
    }

}
