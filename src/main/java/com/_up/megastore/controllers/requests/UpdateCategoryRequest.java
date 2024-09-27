package com._up.megastore.controllers.requests;

import com._up.megastore.data.pipes.CategoryName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record UpdateCategoryRequest(
        @NotBlank(message = "Category name must not be blank")
        @Size(max = 20, message = "Category name must be less than 20 characters")
        @CategoryName
        String name,

        @NotBlank(message = "Category description must not be blank")
        @Size(max = 50, message = "Category description must be less than 50 characters")
        String description,

        UUID superCategoryId

) {}
