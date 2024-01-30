package liubomyr.stepanenko.bookstore.repository.category;

import liubomyr.stepanenko.bookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
