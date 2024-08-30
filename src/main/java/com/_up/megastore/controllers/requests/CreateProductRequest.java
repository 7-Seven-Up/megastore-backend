package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateProductRequest(

        @NotNull(message = "Product name must not be null")
        @Size(max = 30, message = "Product name must be less than 30 characters")
        String name,

        @NotNull(message = "Product description must not be null")
        @Size(max = 80, message = "Product description must be less than 30 characters")
        String description,

        @NotNull(message = "Product price must not be null")
        @Min(value = 0, message = "Price must be 0 or positive")
        Double price,

        @NotNull(message = "Product stock must not be null")
        @Min(value = 0, message = "Product stock must be 0 or positive")
        Integer stock,

        @NotNull(message = "Product color must not be null")
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid color format")
        String color,

        @NotNull(message = "Size ID must not be null")
        UUID sizeId,

        UUID variantOfId
) {}