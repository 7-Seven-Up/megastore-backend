package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.IProductController;
import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.services.interfaces.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController implements IProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile[] multipartFiles) {
        return productService.saveProduct(createProductRequest, multipartFiles);
    }

    @Override
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest, MultipartFile[] multipartFiles) {
       return productService.updateProduct(productId, updateProductRequest, multipartFiles);

    }

    @Override
    public void deleteProduct(UUID productId) {
        productService.deleteProduct(productId);
    }
    @Override
    public ProductResponse restoreProduct(UUID productId) {
        return productService.restoreProduct(productId);
    }

    @Override
    public ProductResponse getProduct(UUID productId) {
        return productService.getProduct(productId);
    }

    @Override
    public List<ProductResponse> getProductVariants(UUID productId) {
        return productService.getProductVariants(productId);
    }

    @Override
    public Page<ProductResponse> getProducts(int page, int pageSize, String name) {
        return productService.getProducts(page, pageSize, name);
    }

}