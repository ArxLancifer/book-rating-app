package com.arx.bookratingapi.model.dto.gutendex;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Author(
        String name,

        @JsonProperty("birth_year")
        Integer birthYear,

        @JsonProperty("death_year")
        Integer deathYear
){}
