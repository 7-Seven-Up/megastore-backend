package com._up.megastore.controllers.responses;

import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String name,
        String description,
        double price,
        List<String> imagesURLS,
        int stock,
        String color,
        String sizeName,
        UUID categoryId,
        String categoryName,
        UUID variantOfId,
        String variantOfName,
        Boolean hasVariants
) {}