package com.arx.bookratingapi.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PagedResults<T> {

    Integer count;
    String nextPageUri;
    String previousPageUri;
    List<T> results;

    public static <T> PagedResults<T> of(
            Integer count,
            String nextPageUri,
            String previousPageUri,
            List<T> results) {
        return new PagedResults<>(count, nextPageUri, previousPageUri, results);
    }
}
