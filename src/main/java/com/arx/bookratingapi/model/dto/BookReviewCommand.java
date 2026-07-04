package com.arx.bookratingapi.model.dto;

import jakarta.validation.constraints.*;

public record BookReviewCommand(

        @NotNull(message = "Book ID cannot be null")
        Long bookId,

        @NotNull(message = "Please add a rating")
        @Digits(integer = 1, fraction = 1, message = "Rating values between 0.0 and 5.0")
        @DecimalMin(value = "0.0", message = "Rating must be greater than or equal to 0")
        @DecimalMax(value = "5.0", message = "Rating must be less than or equal to 5")
        Float rating,

        @Size(max = 1000, message = "Review must be less than 1000 characters")
        String review

        ) {
}
