package liubomyr.stepanenko.bookstore.repository.book;

import java.math.BigDecimal;
import liubomyr.stepanenko.bookstore.dto.book.BookSearchParametersDto;
import liubomyr.stepanenko.bookstore.model.Book;
import liubomyr.stepanenko.bookstore.repository.SpecificationBuilder;
import liubomyr.stepanenko.bookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> specification = Specification.where(null);
        if (searchParametersDto.title() != null && !searchParametersDto.title().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParametersDto.title()));
        }
        if (searchParametersDto.author() != null && !searchParametersDto.author().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParametersDto.author()));
        }
        if (searchParametersDto.minPrice() != null || searchParametersDto.maxPrice() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("price")
                    .getSpecification(new BigDecimal[]{searchParametersDto.minPrice(),
                            searchParametersDto.maxPrice()}));
        }
        return specification;
    }
}
