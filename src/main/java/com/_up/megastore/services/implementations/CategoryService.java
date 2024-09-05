package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.data.model.Category;
import com._up.megastore.data.repositories.ICategoryRepository;
import com._up.megastore.services.interfaces.ICategoryService;
import com._up.megastore.services.mappers.CategoryMapper;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    public CategoryResponse deleteCategory(UUID categoryId){
        ifCategoryExistThrowException(categoryId);
        ifCategorySubcategoriesExistThrowException(categoryId);
        Category category = ifCategoryIsNotDeletedThrowException(categoryId);
        category.setDeleted(true);
        return CategoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void ifCategorySubcategoriesExistThrowException(UUID categoryId){
        if(!findCategoryByIdOrThrowException(categoryId).getSubCategories().isEmpty()){
            throw new DataIntegrityViolationException("Category with id "+ categoryId + " has subcategories and cannot be deleted.");
        }
    }
    public void ifCategoryExistThrowException (UUID categoryId){
        if (!categoryRepository.existsById(categoryId)) {
            throw new NoSuchElementException("Category with id " + categoryId + " does not exist.");
        }
    }

    public Category ifCategoryIsNotDeletedThrowException(UUID categoryId){
        return categoryRepository.findByCategoryIdAndDeletedIsFalse(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category with id " + categoryId + " is deleted."));
    }
}