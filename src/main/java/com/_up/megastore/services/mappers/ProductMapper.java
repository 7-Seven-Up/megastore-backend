package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Category;
import com._up.megastore.data.model.Product;
import com._up.megastore.data.model.ProductImage;
import com._up.megastore.data.model.Size;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product) {
        UUID variantOfId = getVariantOfId(product.getVariantOf());
        List<String> imagesURLS = getImagesURLs(product);

        return new ProductResponse(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                imagesURLS,
                product.getStock(),
                product.getColor(),
                product.getSize().getName(),
            variantOfId
        );
    }

    public static Product toProduct(CreateProductRequest createProductRequest, Size size, Category category, List<ProductImage> images, Product variantOf) {
        return Product.builder()
                .name(createProductRequest.name())
                .description(createProductRequest.description())
                .price(createProductRequest.price())
                .stock(createProductRequest.stock())
                .color(createProductRequest.color())
                .size(size)
                .category(category)
                .images(images)
                .variantOf(variantOf)
                .build();
    }

    private static UUID getVariantOfId(Product variant) {
        return variant != null ? variant.getProductId() : null;
    }

    private static List<String> getImagesURLs(Product product) {
        return product.getImages().stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList());
    }

}