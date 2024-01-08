package liubomyr.stepanenko.bookstore.dto;

import java.math.BigDecimal;

public record BookSearchParametersDto(
        String title,
        String author,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {
}
