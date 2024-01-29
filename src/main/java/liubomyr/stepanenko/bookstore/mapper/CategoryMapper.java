package liubomyr.stepanenko.bookstore.mapper;

import liubomyr.stepanenko.bookstore.config.MapperConfig;
import liubomyr.stepanenko.bookstore.dto.category.CategoryDto;
import liubomyr.stepanenko.bookstore.dto.category.CreateCategoryRequestDto;
import liubomyr.stepanenko.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    void updateCategoryFromDto(CreateCategoryRequestDto dto, @MappingTarget Category category);

    Category toModel(CreateCategoryRequestDto dto);
}
