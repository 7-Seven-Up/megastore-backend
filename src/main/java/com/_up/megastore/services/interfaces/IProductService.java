package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {

    ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile[] multipartFiles);

    Product findProductByIdOrThrowException(UUID productId);

    void deleteProduct(UUID productId);

    ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest, MultipartFile[] multipartFiles);

    ProductResponse restoreProduct(UUID productId);

    ProductResponse getProduct(UUID productId);

    List<ProductResponse> getProductVariants(UUID productId);

    Page<ProductResponse> getProducts(int page, int pageSize, String name, String category);

    Page<ProductResponse> getDeletedProducts(int page, int pageSize);

    void discountProductStock(Integer quantity, Product product);

}