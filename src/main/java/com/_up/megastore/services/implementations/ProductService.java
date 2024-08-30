package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Product;
import com._up.megastore.data.model.Size;
import com._up.megastore.data.repositories.IProductRepository;
import com._up.megastore.services.interfaces.IProductService;
import com._up.megastore.services.interfaces.ISizeService;
import com._up.megastore.services.mappers.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ISizeService sizeService;

    public ProductService(IProductRepository productRepository, ISizeService sizeService) {
        this.productRepository = productRepository;
        this.sizeService = sizeService;
    }

    @Override
    public ProductResponse saveProduct(CreateProductRequest createProductRequest) {
        Size size = sizeService.findSizeByIdOrThrowException(createProductRequest.sizeId());
        Product variantOf = createProductRequest.variantOfId() != null
                ? findProductByIdOrThrowException(createProductRequest.variantOfId())
                : null;

        Product newProduct = ProductMapper.toProduct(createProductRequest, size, variantOf);
        return ProductMapper.toProductResponse( productRepository.save(newProduct) );
    }

    @Override
    public Product findProductByIdOrThrowException(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product with id " + productId + " does not exist."));
    }

}