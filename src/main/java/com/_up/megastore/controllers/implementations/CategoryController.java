package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.ICategoryController;
import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.services.interfaces.ICategoryService;
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

}