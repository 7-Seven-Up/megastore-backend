package com._up.megastore.controllers.responses;

import java.util.UUID;

public record CategoryResponse(
        UUID categoryId,
        String name,
        String description,
        String superCategoryName
) {}