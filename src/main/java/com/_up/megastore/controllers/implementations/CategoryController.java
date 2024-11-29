package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.ICategoryController;
import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.requests.UpdateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.services.interfaces.ICategoryService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements ICategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryResponse saveCategory(CreateCategoryRequest createCategoryRequest) {
        return categoryService.saveCategory(createCategoryRequest);
    }

    @Override
    public CategoryResponse readCategory(UUID categoryId){
        return categoryService.readCategory(categoryId);
    }

    @Override
    public Page<CategoryResponse> readAllCategories(int page, int pageSize, String name){
        return categoryService.readAllCategories(page, pageSize, name);
    }

    @Override
    public Page<CategoryResponse> readDeletedCategories(int page, int pageSize) {
        return categoryService.readDeletedCategories(page, pageSize);
    }

    @Override
    public void deleteCategory(UUID categoryId){
        categoryService.deleteCategory(categoryId);
    }
  
    @Override
    public CategoryResponse restoreCategory(UUID categoryId) {
        return categoryService.restoreCategory(categoryId);
    }
  
    @Override
    public CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest updateCategoryRequest){
        return categoryService.updateCategory(categoryId,updateCategoryRequest);
    }
}