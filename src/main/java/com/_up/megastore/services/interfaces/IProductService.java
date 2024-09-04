package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IProductService {

    ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile);

    Product findProductByIdOrThrowException(UUID productId);

    ProductResponse deleteProduct(UUID productId);

    ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest, MultipartFile multipartFile);

}