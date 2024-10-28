package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CancelOrderRequest(
        @NotNull(message = "Category name must not be null")
        @Size(min = 5, max = 50, message = "Category name must be between 5 and 50 characters")
        String reason
) {}