package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.requests.UpdateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.data.model.Category;
import com._up.megastore.data.repositories.ICategoryRepository;
import com._up.megastore.services.interfaces.ICategoryService;
import com._up.megastore.services.mappers.CategoryMapper;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
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
    public CategoryResponse readCategory(UUID categoryId){
        Category category = findCategoryByIdOrThrowException(categoryId);
        return CategoryMapper.toCategoryResponse(category);
    }

    @Override
    public Page<CategoryResponse> readAllCategories(int page, int pageSize, String name){
        Pageable pageable = PageRequest.of(page, pageSize);
        return categoryRepository.findCategoryByDeletedIsFalseAndNameContainingIgnoreCase(name,pageable).map(CategoryMapper::toCategoryResponse);
    }

    @Override
    public void deleteCategory(UUID categoryId){
        Category category = findCategoryByIdOrThrowException(categoryId);
        ifCategorySubcategoriesExistThrowException(categoryId);
        ifCategoryIsNotDeletedThrowException(category);
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    public void ifCategorySubcategoriesExistThrowException(UUID categoryId){
        if(!findCategoryByIdOrThrowException(categoryId).getSubCategories().isEmpty()){
            throw new DataIntegrityViolationException("Category with id "+ categoryId + " has subcategories and cannot be deleted.");
        }
    }

    public void ifCategoryIsNotDeletedThrowException(Category category){
        if(category.isDeleted()){
           throw new IllegalStateException("Category with id " + category.getCategoryId() + " is already deleted.");
        }
    }
  
    @Override
    public CategoryResponse restoreCategory(UUID categoryId){
        Category category = findCategoryByIdOrThrowException(categoryId);
        validateCategoryForRestoration(category);
        category.setDeleted(false);
        return CategoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest updateCategoryRequest){
        Category category = findCategoryByIdOrThrowException(categoryId);
        category.setName(updateCategoryRequest.name());
        category.setDescription(updateCategoryRequest.description());
        UUID superCategoryId = updateCategoryRequest.superCategoryId();
        ifSuperCategoryExistUpdateCategorySuperCategory(superCategoryId,category);
        return CategoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void ifSuperCategoryExistUpdateCategorySuperCategory(UUID superCategoryId, Category category) {
        if (superCategoryId != null) {
            Category superCategory = findCategoryByIdOrThrowException(superCategoryId);
            category.setSuperCategory(superCategory);
        } else {
            category.setSuperCategory(null);
        }
    }

    private void validateCategoryForRestoration(Category category) {
        throwExceptionIfCategoryIsNotDeleted(category);
        throwExceptionIfCategoryWithNameAlreadyExists(category);
    }

    private void throwExceptionIfCategoryIsNotDeleted(Category category){
        if(!category.isDeleted()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category with id " + category.getCategoryId() + " is not deleted.");
        }
    }

    private void throwExceptionIfCategoryWithNameAlreadyExists(Category category) {
        if (categoryRepository.existsByNameAndDeletedIsFalse(category.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category with name "+ category.getName() +" already exists and is not deleted.");
        }
    }

}