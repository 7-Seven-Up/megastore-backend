package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.requests.UpdateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.data.model.Category;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ICategoryService {

    CategoryResponse saveCategory(CreateCategoryRequest createCategoryRequest);

    Category findCategoryByIdOrThrowException(UUID categoryId);


    CategoryResponse readCategory(UUID categoryRequest);

    Page<CategoryResponse> readAllCategories(int page, int pageSize, String name);

    void deleteCategory(UUID categoryId);

    CategoryResponse restoreCategory (UUID categoryId);
  
    CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest updateCategoryRequest);

}