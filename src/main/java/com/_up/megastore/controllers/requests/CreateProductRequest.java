package com._up.megastore.controllers.requests;

import java.util.UUID;

public record CreateProductRequest(

        String name,
        String description,
        Double price,
        String imageURL,
        Integer stock,
        String color,
        UUID sizeId,
        UUID variantOfId

) {}