package com.arx.bookratingapi.controller;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arx.bookratingapi.model.dto.PagedResults;
import com.arx.bookratingapi.model.dto.gutendex.SingleBookResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.File;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EnableWireMock(
    @ConfigureWireMock(name = "mock-book-service", port = 8081)
)
public class BookSearchControllerE2ETest {

  @InjectWireMock("mock-book-service")
  WireMockServer mockBookService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @DisplayName("Method searchByTitle returns a json response with previous and next page")
  public void testSearchByTitle_Returns_PagedResult() throws Exception {

    // Setup
    String gutendexMockResponse = FileUtils.readFileToString(new File("./src/test/resources/gutendex-mock-responses/bookTitleLordResponse.json"), StandardCharsets.UTF_8);

    mockBookService.stubFor(get("/books/?search=lord&page=1").willReturn(aResponse()
        .withHeader("Content-Type", "application/json")
        .withStatus(200)
        .withBody(gutendexMockResponse)));

    // Run tests and verify the results

    MvcResult apiResponseResult = mockMvc.perform(MockMvcRequestBuilders.get("/books/search")
            .param("title", "lord")
            .param("page", "1"))
        .andExpect(status().isOk()).andReturn();

    PagedResults<SingleBookResponse> responsePagedResults = objectMapper.readValue(apiResponseResult.getResponse().getContentAsString(),new TypeReference<PagedResults<SingleBookResponse>>() {});


    assertThat(responsePagedResults.getNextPageUri()).isEqualTo("http://localhost/books/search?page=2");
    assertThat(responsePagedResults.getPreviousPageUri()).isNull();

  }

}
