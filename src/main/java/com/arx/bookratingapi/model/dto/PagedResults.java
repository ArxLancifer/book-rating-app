package com.arx.bookratingapi.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
