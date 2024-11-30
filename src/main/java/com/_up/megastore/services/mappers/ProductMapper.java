package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Category;
import com._up.megastore.data.model.Product;
import com._up.megastore.data.model.ProductImage;
import com._up.megastore.data.model.Size;
import java.util.List;
import java.util.UUID;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductResponse toProductResponse(Product product) {
        UUID variantOfId = getVariantOfId(product.getVariantOf());
        String variantOfName = getVariantOfName(product.getVariantOf());
        Boolean hasVariants = product.getVariants().stream()
            .anyMatch(variant -> !variant.isDeleted());
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
                variantOfId,
                variantOfName,
                hasVariants
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

    private static String getVariantOfName(Product variant) {
        return variant != null ? variant.getName() : null;
    }

    private static UUID getVariantOfId(Product variant) {
        return variant != null ? variant.getProductId() : null;
    }

    private static List<String> getImagesURLs(Product product) {
        return product.getImages().stream()
                .map(ProductImage::getUrl)
                .toList();
    }

}