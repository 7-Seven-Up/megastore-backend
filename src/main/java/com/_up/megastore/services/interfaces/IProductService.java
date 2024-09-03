package com._up.megastore.services.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;

public interface IProductService {

    ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile);

    ProductResponse getProduct(UUID productId);

    ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest, MultipartFile multipartFile);

    Page<ProductResponse> getProducts(int page, int pageSize, String sortBy, String filter);
}
