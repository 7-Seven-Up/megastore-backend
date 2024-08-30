package com._up.megastore.services.mappers;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Product;

@Component
public class ProductMapper {

    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(product.getName(),
                product.getDescription(), product.getPrice(), product.getImageURL(), product.getColor(),
                product.getSize().getSizeId(), product.isDeleted(), product.getStock(), new ArrayList<>(),
                product.getProductId());
    }
}
