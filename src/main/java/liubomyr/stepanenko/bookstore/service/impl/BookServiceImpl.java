package liubomyr.stepanenko.bookstore.service.impl;

import java.util.List;
import java.util.Optional;
import liubomyr.stepanenko.bookstore.dto.BookDto;
import liubomyr.stepanenko.bookstore.dto.BookSearchParametersDto;
import liubomyr.stepanenko.bookstore.dto.CreateBookRequestDto;
import liubomyr.stepanenko.bookstore.exception.EntityNotFoundException;
import liubomyr.stepanenko.bookstore.mapper.BookMapper;
import liubomyr.stepanenko.bookstore.model.Book;
import liubomyr.stepanenko.bookstore.repository.book.BookRepository;
import liubomyr.stepanenko.bookstore.repository.book.BookSpecificationBuilder;
import liubomyr.stepanenko.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto dto) {
        Book book = bookMapper.toModel(dto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto findById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookMapper.toDto(bookOptional.get());
        }
        throw new EntityNotFoundException("Can't find the book with id = " + id);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParametersDto) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParametersDto);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto dto) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book bookForUpdate = bookOptional.orElseThrow(
                () -> new EntityNotFoundException("Can't find the book with id = " + id)
        );
        bookMapper.updateBookFromDto(dto, bookForUpdate);
        Book updatedBook = bookRepository.save(bookForUpdate);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
