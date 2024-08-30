package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
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

    public static Product toProduct(CreateProductRequest createProductRequest, Size size, Product variantOf) {
        return Product.builder()
                .name(createProductRequest.name())
                .description(createProductRequest.description())
                .price(createProductRequest.price())
                .imageURL(createProductRequest.imageURL())
                .stock(createProductRequest.stock())
                .color(createProductRequest.color())
                .size(size)
                .variantOf(variantOf)
                .build();
    }

}