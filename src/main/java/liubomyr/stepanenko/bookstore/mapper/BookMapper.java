package liubomyr.stepanenko.bookstore.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import liubomyr.stepanenko.bookstore.config.MapperConfig;
import liubomyr.stepanenko.bookstore.dto.book.BookDto;
import liubomyr.stepanenko.bookstore.dto.book.BookDtoWithoutCategoryIds;
import liubomyr.stepanenko.bookstore.dto.book.CreateBookRequestDto;
import liubomyr.stepanenko.bookstore.model.Book;
import liubomyr.stepanenko.bookstore.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    void updateBookFromDto(CreateBookRequestDto dto, @MappingTarget Book book);

    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoryIds);
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        return Optional.ofNullable(id)
                .map(Book::new)
                .orElse(null);
    }
}
