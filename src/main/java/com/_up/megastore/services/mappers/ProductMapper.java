package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Category;
import com._up.megastore.data.model.Product;
import com._up.megastore.data.model.Size;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product) {
        String variantName = product.getVariantOf() != null
                ? product.getVariantOf().getName()
                : null;

        return new ProductResponse(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageURL(),
                product.getStock(),
                product.getColor(),
                product.getSize().getName(),
                variantName
        );
    }

    public static Product toProduct(CreateProductRequest createProductRequest, Size size, Category category, Product variantOf, String imageURL) {
        return Product.builder()
                .name(createProductRequest.name())
                .description(createProductRequest.description())
                .price(createProductRequest.price())
                .imageURL(imageURL)
                .stock(createProductRequest.stock())
                .color(createProductRequest.color())
                .size(size)
                .category(category)
                .variantOf(variantOf)
                .build();
    }

}