package com._up.megastore.services.implementations;

import java.util.List;
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
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product get(UUID id) {
        return productRepository.findById(id).get();
    }

    
}
