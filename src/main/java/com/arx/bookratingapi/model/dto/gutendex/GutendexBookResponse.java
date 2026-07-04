package com.arx.bookratingapi.model.dto.gutendex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexBookResponse(Integer count, String next, String previous, List<SingleBookResponse> results) { }


