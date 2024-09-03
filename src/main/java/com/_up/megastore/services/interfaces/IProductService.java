package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IProductService {

    ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile);

    ProductResponse getProduct(UUID productId);

    Page<ProductResponse> getProducts(int page, int pageSize, String sortBy, String filter);
}
