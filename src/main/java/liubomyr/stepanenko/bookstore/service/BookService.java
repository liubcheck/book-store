package liubomyr.stepanenko.bookstore.service;

import java.util.List;
import liubomyr.stepanenko.bookstore.dto.book.BookDto;
import liubomyr.stepanenko.bookstore.dto.book.BookSearchParametersDto;
import liubomyr.stepanenko.bookstore.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    BookDto findById(Long id);

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> search(BookSearchParametersDto searchParametersDto);

    BookDto update(Long id, CreateBookRequestDto dto);

    void delete(Long id);
}
