package liubomyr.stepanenko.bookstore.repository.book.specification;

import java.math.BigDecimal;
import liubomyr.stepanenko.bookstore.model.Book;
import liubomyr.stepanenko.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "price";
    }

    @Override
    public Specification<Book> getSpecification(Object param) {
        if (param instanceof BigDecimal[] range) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("price"), range[0], range[1]);
        } else {
            throw new IllegalArgumentException("Invalid parameter type for price range");
        }
    }
}
