package com._up.megastore.data.reports;

public record SoldProductResponse(
        String name,
        Integer quantity,
        Double totalSold
) {
}
