package liubomyr.stepanenko.bookstore.repository.book.specification;

import liubomyr.stepanenko.bookstore.model.Book;
import liubomyr.stepanenko.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "title";
    }

    @Override
    public Specification<Book> getSpecification(Object param) {
        return (root, query, criteriaBuilder)
                -> root.get("title").in(param);
    }
}
