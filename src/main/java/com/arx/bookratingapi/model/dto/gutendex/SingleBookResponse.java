package com.arx.bookratingapi.model.dto.gutendex;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SingleBookResponse(
        Long id,
        String title,
        List<Author> authors,
        List<String> languages,
        @JsonProperty("download_count")
        Integer downloadCount
) { }
