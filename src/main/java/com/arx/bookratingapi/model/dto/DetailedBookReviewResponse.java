package com.arx.bookratingapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DetailedBookReviewResponse(
        Long id,
        String title,
        List<GutendexBookResponse.Author> authors,
        List<String> languages,
        @JsonProperty("download_count") Integer downloadCount,
        Float rating,
        List<String> reviews
){}
