package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.data.model.Category;

public class CategoryMapper {

    public static CategoryResponse toCategoryResponse(Category category) {
        String superCategoryName = category.getSuperCategory() != null
                ? category.getSuperCategory().getName()
                : null;

        return new CategoryResponse(category.getCategoryId(), category.getName(), category.getDescription(), superCategoryName);
    }

    public static Category toCategory(CreateCategoryRequest createCategoryRequest, Category superCategory) {
        return Category.builder()
                .name(createCategoryRequest.name())
                .description(createCategoryRequest.description())
                .superCategory(superCategory)
                .build();
    }

}