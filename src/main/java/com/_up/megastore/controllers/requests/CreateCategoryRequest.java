package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateCategoryRequest(

        @NotNull(message = "Category name must not be null")
        @Size(max = 20, message = "Category name must be less than 30 characters")
        String name,

        @NotNull(message = "Category description must not be null")
        @Size(max = 50, message = "Category description must be less than 30 characters")
        String description,

        UUID superCategoryId
) {}