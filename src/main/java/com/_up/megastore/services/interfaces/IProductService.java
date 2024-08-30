package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Product;

import java.util.UUID;

public interface IProductService {

    ProductResponse saveProduct(CreateProductRequest createProductRequest);

    Product findProductByIdOrThrowException(UUID productId);

}