package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateSizeRequest (
        @NotBlank(message = "Size name must not be null and blank")
        @Size(max = 20, message = "Size name must be less than 20 characters")
        String name,

        @NotNull(message = "Size description must not be null")
        @Size(max = 50, message = "Size description must be less than 50 characters")
        String description
){}
