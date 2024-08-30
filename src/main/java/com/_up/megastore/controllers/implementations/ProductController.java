package com._up.megastore.controllers.implementations;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com._up.megastore.controllers.interfaces.IProductController;
import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.services.interfaces.IProductService;

@RestController
public class ProductController implements IProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile) {
        return productService.saveProduct(createProductRequest, multipartFile);
    }

    @Override
    public ProductResponse findProductById(UUID id) {
        return productService.getProduct(id);
    }

    @Override
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest, MultipartFile multipartFile) {
        return productService.updateProduct(productId, updateProductRequest, multipartFile);

    }

    @Override
    public Page<ProductResponse> findProducts(int page, int pageSize, String sortBy, String name) {
        return productService.getProducts(page, pageSize, sortBy, name);
    }
}
