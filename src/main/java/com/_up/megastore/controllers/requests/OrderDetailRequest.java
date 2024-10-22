package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderDetailRequest(

        @NotNull(message = "Product ID must not be null")
        UUID productId,

        @NotNull(message = "Quantity must not be null")
        @Min(value = 0, message = "Quantity must be 0 or positive")
        Integer quantity
) {
}