package com._up.megastore.services.implementations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com._up.megastore.data.model.Product;
import com._up.megastore.data.repositories.IProductRepository;
import com._up.megastore.services.interfaces.IProductService;

@Service
public class ProductService implements IProductService {
    
    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductByIdOrThrowException(UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("Product with id " + productId + " does not exist."));
    }

    
}
