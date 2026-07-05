package com.arx.bookratingapi.model.dto;

import com.arx.bookratingapi.model.dao.MonthlyBookRating;
import com.arx.bookratingapi.model.dto.gutendex.SingleBookResponse;
import java.util.List;

public record MonthlyBookRatingResponse(SingleBookResponse bookResponse, List<MonthlyBookRating> monthlyBookRatings) {}
