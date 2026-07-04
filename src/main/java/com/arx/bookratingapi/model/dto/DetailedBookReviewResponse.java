package com.arx.bookratingapi.model.dto;

import com.arx.bookratingapi.model.dto.gutendex.Author;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DetailedBookReviewResponse(
        Long id,
        String title,
        List<Author> authors,
        List<String> languages,
        @JsonProperty("download_count") Integer downloadCount,
        Float rating,
        List<String> reviews
){}
