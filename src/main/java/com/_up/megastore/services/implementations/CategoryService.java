package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.data.model.Category;
import com._up.megastore.data.repositories.ICategoryRepository;
import com._up.megastore.services.interfaces.ICategoryService;
import com._up.megastore.services.mappers.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse saveCategory(CreateCategoryRequest createCategoryRequest) {
        Category superCategory = getSuperCategory(createCategoryRequest.superCategoryId());
        Category category = CategoryMapper.toCategory(createCategoryRequest, superCategory);
        return CategoryMapper.toCategoryResponse( categoryRepository.save(category) );
    }

    @Override
    public Category findCategoryByIdOrThrowException(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category with id " + categoryId + " does not exist."));
    }

    private Category getSuperCategory(UUID superCategoryId) {
        return superCategoryId != null ? findCategoryByIdOrThrowException(superCategoryId) : null;
    }

}