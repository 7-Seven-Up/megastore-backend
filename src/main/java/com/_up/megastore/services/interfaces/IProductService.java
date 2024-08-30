package com._up.megastore.services.interfaces;

import java.util.List;
import java.util.UUID;

import com._up.megastore.data.model.Product;

public interface IProductService {
    List<Product> findAllProducts();
    Product findProductByIdOrThrowException(UUID id);
}
