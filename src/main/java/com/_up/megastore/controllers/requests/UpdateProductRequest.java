package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record UpdateProductRequest(
    @NotBlank(message = "Product name must not be blank")
    @Size(min = 3, max = 30, message = "Product name must be between 3 and 30 characters")
    String name,

    @Size(max = 80, message = "Product description must be less than 80 characters")
    String description,

    @NotNull(message = "Product price must not be null")
    @Min(value = 0, message = "Price must be 0 or positive")
    Double price,

    @NotNull(message = "Category ID must not be null")
    UUID categoryId,

    UUID variantOfId
){}
