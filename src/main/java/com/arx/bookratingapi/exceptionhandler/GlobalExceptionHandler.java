package com.arx.bookratingapi.exceptionhandler;

import com.arx.bookratingapi.model.dto.GutendexErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ProblemDetail> handleRestClientResponseException(RestClientResponseException e){

        ProblemDetail problemDetail = ProblemDetail.forStatus(e.getStatusCode());
        problemDetail.setTitle(e.getStatusText());
        problemDetail.setDetail(Objects.requireNonNull(e.getResponseBodyAs(GutendexErrorResponse.class)).detail());
        return new ResponseEntity<>(problemDetail, e.getStatusCode());

    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ProblemDetail> handleRestClientException(RestClientException e){

        log.warn("RestClientException occurred: {} ", e.getMessage(), e);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        problemDetail.setDetail("Something went wrong. Please try later again, if the problem remains contact the support team support-team@testmail.com");
        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleBadRequestException(MethodArgumentNotValidException e){

        Map<String, List<String>> fieldErrors = e.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.groupingBy(
                FieldError::getField,
                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                )
            );

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        problemDetail.setDetail("Validation failed for one or more fields.");
        problemDetail.setProperty("fieldErrors", fieldErrors);


        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}
