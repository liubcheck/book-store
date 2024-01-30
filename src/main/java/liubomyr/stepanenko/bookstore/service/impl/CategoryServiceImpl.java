package liubomyr.stepanenko.bookstore.service.impl;

import java.util.List;
import java.util.Optional;
import liubomyr.stepanenko.bookstore.dto.category.CategoryDto;
import liubomyr.stepanenko.bookstore.dto.category.CreateCategoryRequestDto;
import liubomyr.stepanenko.bookstore.exception.EntityNotFoundException;
import liubomyr.stepanenko.bookstore.mapper.CategoryMapper;
import liubomyr.stepanenko.bookstore.model.Category;
import liubomyr.stepanenko.bookstore.repository.category.CategoryRepository;
import liubomyr.stepanenko.bookstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        Category category = categoryMapper.toModel(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find the category with id = " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto dto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        Category categoryForUpdate = categoryOptional.orElseThrow(
                () -> new EntityNotFoundException("Can't find the category with id = " + id)
        );
        categoryMapper.updateCategoryFromDto(dto, categoryForUpdate);
        Category updatedCategory = categoryRepository.save(categoryForUpdate);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
