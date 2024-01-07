package liubomyr.stepanenko.bookstore.repository;

import liubomyr.stepanenko.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
