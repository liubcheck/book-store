package liubomyr.stepanenko.bookstore.service;

import java.util.List;
import liubomyr.stepanenko.bookstore.dto.BookDto;
import liubomyr.stepanenko.bookstore.dto.BookSearchParametersDto;
import liubomyr.stepanenko.bookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    BookDto findById(Long id);

    List<BookDto> findAll();

    List<BookDto> search(BookSearchParametersDto searchParametersDto);

    BookDto update(Long id, CreateBookRequestDto dto);

    void delete(Long id);
}
