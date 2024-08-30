package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record CreateProductRequest(

        @NotBlank(message = "Product name must not be null")
        @Size(max = 30, message = "Product name must be less than 30 characters")
        String name,

        @NotBlank(message = "Product description must not be null")
        @Size(max = 80, message = "Product description must be less than 80 characters")
        String description,

        @Min(value = 0, message = "Price must be 0 or positive")
        double price,

        @Min(value = 0, message = "Product stock must be 0 or positive")
        int stock,

        @NotNull(message = "Product color must not be null")
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid color format")
        String color,

        @NotNull(message = "Size ID must not be null")
        UUID sizeId,

        @NotNull(message = "Category ID must not be null")
        UUID categoryId,

        UUID variantOfId
) {}