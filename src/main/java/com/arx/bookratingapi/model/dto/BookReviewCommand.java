package com.arx.bookratingapi.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record BookReviewCommand(

        @NotNull(message = "Book ID cannot be null")
        Long bookId,

        @NotNull(message = "Please add a rating")
        @Range(min = 0, max = 5, message = "Rating must be between 0 and 5")
        Short rating,

        @Size(max = 1000, message = "Review must be less than 1000 characters")
        String review

        ) {
}
