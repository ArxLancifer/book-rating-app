package com.arx.bookratingapi.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arx.bookratingapi.model.dto.BookReviewCommand;
import com.arx.bookratingapi.model.entity.BookReview;
import com.arx.bookratingapi.repository.BookReviewRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@EnableWireMock(
    @ConfigureWireMock(name = "mock-book-service", port = 8081)
)
public class BookReviewControllerE2ETest {

  @InjectWireMock("mock-book-service")
  WireMockServer mockBookService;

  @Autowired
  BookReviewRepository bookReviewRepository;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Container
  @ServiceConnection
  static PostgreSQLContainer postgresContainer = new PostgreSQLContainer(
      "postgres:16-alpine").withDatabaseName("book_rating_db").withUsername("postgres")
      .withPassword("postgres");

  @DynamicPropertySource
  public static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", ()-> postgresContainer.getJdbcUrl() + "?currentSchema=book_reviews");
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @Test
  @DisplayName("Method postReview successfully adds a review in database")
  void postReview_saves_review() throws Exception {

    // Setup
    String gutendexMockResponse = FileUtils.readFileToString(new File("./src/test/resources/gutendex-mock-responses/bookid1.json"), StandardCharsets.UTF_8);

    BookReviewCommand bookReview = new BookReviewCommand(1L, 5F, "A great book to read");

    stubFor(get("/books/1/").willReturn(aResponse()
        .withHeader("Content-Type", "application/json")
        .withStatus(200)
        .withBody(gutendexMockResponse)));

    // Run test Verify the results
    mockMvc.perform(post("/reviews").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(bookReview)))
        .andExpect(status().isNoContent());

    // Verify the results
    List<BookReview> reviews = bookReviewRepository.findAll();

    assertThat(reviews.size()).isEqualTo(1);
    assertThat(reviews.getFirst().getReview()).isEqualTo(bookReview.review());

  }

  @Test
  @DisplayName("Method postReview returns 404 when book is not found in Gutendex API")
  void postReview_returns_404_when_book_not_found() throws Exception {

    // Setup
    BookReviewCommand bookReview = new BookReviewCommand(1L, 5F, "A great book to read");

    mockBookService.stubFor(get(urlEqualTo( "/books/1/")).willReturn(aResponse()
        .withHeader("Content-Type", "application/json")
        .withBody("{\"detail\": \"No Book matches the given query.\"}")
        .withStatus(404)));

    // Run test Verify the results
    mockMvc.perform(post("/reviews").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(bookReview)))
        .andExpect(status().isNotFound());

  }


}
