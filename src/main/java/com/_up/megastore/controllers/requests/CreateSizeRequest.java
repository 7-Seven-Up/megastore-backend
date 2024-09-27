package com._up.megastore.controllers.requests;

import com._up.megastore.data.pipes.SizeName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateSizeRequest(

        @NotNull(message = "Size name must not be null")
        @Size(min = 3, max = 20, message = "Size name must be between 3 and 20 characters")
        @SizeName
        String name,

        @NotNull(message = "Size description must not be null")
        @Size(min = 5, max = 50, message = "Size description must be between 5 and 50 characters")
        String description
) {}