package liubomyr.stepanenko.bookstore.repository;

import java.util.List;
import liubomyr.stepanenko.bookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
