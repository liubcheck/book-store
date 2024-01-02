package liubomyr.stepanenko.bookstore.service;

import java.util.List;
import liubomyr.stepanenko.bookstore.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
