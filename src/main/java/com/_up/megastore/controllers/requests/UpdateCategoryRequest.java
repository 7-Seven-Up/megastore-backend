package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateCategoryRequest(
        @NotBlank(message = "Category name must not be blank")
        @Size(min = 3, max = 20, message = "Category name must be between 3 and 20 characters")
        String name,

        @NotBlank(message = "Category description must not be blank")
        @Size(min = 5, max = 50, message = "Category description must be between 5 and 50 characters")
        String description,

        UUID superCategoryId

) {}
