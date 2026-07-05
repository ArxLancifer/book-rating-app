CREATE SCHEMA IF NOT EXISTS book_reviews;

SET search_path TO book_reviews;

CREATE SEQUENCE book_review_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE book_review (
    id BIGINT PRIMARY KEY DEFAULT nextval('book_review_seq'),
    book_id BIGINT NOT NULL,
    rating DECIMAL(2,1) NOT NULL CONSTRAINT chk_rating_range CHECK (rating >= 0 AND rating <= 5),
    review VARCHAR(1000),

    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);