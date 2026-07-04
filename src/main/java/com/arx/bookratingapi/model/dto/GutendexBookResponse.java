package com.arx.bookratingapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexBookResponse(Integer count, String next, String previous, List<GutendexBook> results) {

    public record GutendexBook(
            Long id,
            String title,
            List<Author> authors,
            List<String> languages,
            @JsonProperty("download_count")
            Integer downloadCount
    ) { }

    public record Author(
            String name,

            @JsonProperty("birth_year")
            Integer birthYear,

            @JsonProperty("death_year")
            Integer deathYear
            ){}
}


