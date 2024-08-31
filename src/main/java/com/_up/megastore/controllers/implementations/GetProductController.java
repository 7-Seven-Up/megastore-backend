package com._up.megastore.controllers.implementations;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com._up.megastore.controllers.interfaces.IGetProductController;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Product;
import com._up.megastore.services.implementations.ProductService;
import com._up.megastore.services.mappers.ProductMapper;

@RestController
public class GetProductController implements IGetProductController {

    private ProductService productService;

    @Override
    public ProductResponse findProductById(@PathVariable UUID id) {
        Product product = productService.findProductByIdOrThrowException(id);
        return ProductMapper.toProductResponse(product);
    }
}
