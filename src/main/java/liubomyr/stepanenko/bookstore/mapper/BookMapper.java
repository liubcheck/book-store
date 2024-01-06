package liubomyr.stepanenko.bookstore.mapper;

import liubomyr.stepanenko.bookstore.config.MapperConfig;
import liubomyr.stepanenko.bookstore.dto.BookDto;
import liubomyr.stepanenko.bookstore.dto.CreateBookRequestDto;
import liubomyr.stepanenko.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    void updateBookFromDto(CreateBookRequestDto dto, @MappingTarget Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
