# Book Rating API

Project summary

Book Rating API is a small Spring Boot (4.1.0) REST service written for Java 21 that
provides book search and rating features. It integrates with an external book provider (Gutendex).

Project coordinates (from pom.xml)
- GroupId: com.arx
- ArtifactId: book-rating-api
- Version: 0.0.1-SNAPSHOT
- Java: 21
- Spring Boot parent: 4.1.0

Tech stack and libraries
- Spring Boot: webmvc, validation, data-jpa, cache
- Flyway for DB migrations (flyway-database-postgresql)
- PostgreSQL JDBC driver (runtime)
- Lombok (compile-time convenience, optional)
- Test support: Testcontainers (Postgres), WireMock, Spring Boot test starters

Key features
- Search books by title (external provider integration)
- Post and retrieve detailed reviews for books
- List top-rated books with pagination
- Flyway-managed schema and Postgres runtime
- Integration-friendly test setup using Testcontainers and WireMock

API Endpoints (inferred from controllers)
- POST /reviews
  - Description: Submit a book review.
  - Request: JSON body matching BookReviewCommand (validated).
  - Response: 204 No Content on success.

- GET /reviews/{bookId}
  - Description: Get aggregated review details for a book.
  - Response: DetailedBookReviewResponse (aggregated ratings/comments).

- GET /reviews/top-rated?limit={limit}&page={page}
  - Description: Get top-rated books. `limit` controls page size (required). `page` defaults to 1.
  - Response: PagedResults<SingleBookResponse>

- GET /books/search?title={title}&page={page}
  - Description: Search books by title via the external provider. `page` defaults to 1.
  - Response: PagedResults<SingleBookResponse>