package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateCategoryRequest(
        @NotNull(message = "Category name must not be null")
        @Size(min = 3, max = 20, message = "Category name must be between 3 and 20 characters")
        String name,

        @Size(max = 50, message = "Category description must be less than 50 characters")
        String description,

        UUID superCategoryId

) {}
