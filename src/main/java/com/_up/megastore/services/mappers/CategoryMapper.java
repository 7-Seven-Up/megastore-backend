package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.data.model.Category;
import java.util.UUID;

public class CategoryMapper {

    public static CategoryResponse toCategoryResponse(Category category) {
        String superCategoryName = getSuperCategoryName(category.getSuperCategory());
        UUID superCategoryId = getSuperCategoryId(category.getSuperCategory());
        return new CategoryResponse(category.getCategoryId(), category.getName(), category.getDescription(), superCategoryName, superCategoryId);
    }

    public static Category toCategory(CreateCategoryRequest createCategoryRequest, Category superCategory) {
        return Category.builder()
                .name(createCategoryRequest.name())
                .description(createCategoryRequest.description())
                .superCategory(superCategory)
                .build();
    }

    private static String getSuperCategoryName(Category superCategory) {
        return superCategory != null ? superCategory.getName() : null;
    }

    private static UUID getSuperCategoryId(Category superCategory) {
        return superCategory != null ? superCategory.getCategoryId() : null;
    }

}