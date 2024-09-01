package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Category;
import com._up.megastore.data.model.Product;
import com._up.megastore.data.model.Size;
import com._up.megastore.data.repositories.IProductRepository;
import com._up.megastore.services.interfaces.ICategoryService;
import com._up.megastore.services.interfaces.IFileUploadService;
import com._up.megastore.services.interfaces.IProductService;
import com._up.megastore.services.interfaces.ISizeService;
import com._up.megastore.services.mappers.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ISizeService sizeService;
    private final ICategoryService categoryService;
    private final IFileUploadService fileUploadService;

    public ProductService(IProductRepository productRepository,
                          ISizeService sizeService,
                          ICategoryService categoryService,
                          IFileUploadService fileUploadService) {
        this.productRepository = productRepository;
        this.sizeService = sizeService;
        this.categoryService = categoryService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile) {
        Size size = sizeService.findSizeByIdOrThrowException(createProductRequest.sizeId());
        Category category = categoryService.findCategoryByIdOrThrowException(createProductRequest.categoryId());
        Product variantOf = getVariantOf(createProductRequest.variantOfId());
        String imageURL = saveProductImage(multipartFile);

        Product newProduct = ProductMapper.toProduct(createProductRequest, size, category, variantOf, imageURL);
        return ProductMapper.toProductResponse( productRepository.save(newProduct) );
    }

    @Override
    public ProductResponse getProduct(UUID productId) {
        Product product = findProductByIdOrThrowException(productId);
        return ProductMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getProductByPages(int page, int pageSize, String sortBy, String filter) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        String formattedFilter = "%" + filter + "%";
        return productRepository.findProductsByDeletedIsFalseAndNameLikeIgnoreCase(formattedFilter, pageable)
                .map(ProductMapper::toProductResponse);
    }

    private String saveProductImage(MultipartFile multipartFile) {
        return fileUploadService.uploadImage(multipartFile);
    }

    private Product findProductByIdOrThrowException(UUID productId) {
        return productRepository.findProductByProductIdAndDeletedIsFalse(productId).orElseThrow(() ->
                new NoSuchElementException("Product with id " + productId + " does not exist."));
    }

    private Product getVariantOf(UUID variantOfId) {
        return variantOfId != null ? findProductByIdOrThrowException(variantOfId) : null;
    }

}