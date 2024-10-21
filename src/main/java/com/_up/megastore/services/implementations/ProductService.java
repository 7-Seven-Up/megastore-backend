package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import com._up.megastore.data.model.Category;
import com._up.megastore.data.model.Product;
import com._up.megastore.data.model.ProductImage;
import com._up.megastore.data.model.Size;
import com._up.megastore.data.repositories.IProductRepository;
import com._up.megastore.services.interfaces.ICategoryService;
import com._up.megastore.services.interfaces.IProductImageService;
import com._up.megastore.services.interfaces.IProductService;
import com._up.megastore.services.interfaces.ISizeService;
import com._up.megastore.services.mappers.ProductMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ISizeService sizeService;
    private final ICategoryService categoryService;
    private final IProductImageService productImageService;

    public ProductService(IProductRepository productRepository, ISizeService sizeService, ICategoryService categoryService, IProductImageService productImageService) {
        this.productRepository = productRepository;
        this.sizeService = sizeService;
        this.categoryService = categoryService;
        this.productImageService = productImageService;
    }

    @Override
    @Transactional
    public ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile[] multipartFiles) {
        Size size = sizeService.findSizeByIdOrThrowException(createProductRequest.sizeId());
        Category category = categoryService.findCategoryByIdOrThrowException(createProductRequest.categoryId());
        List<ProductImage> images = productImageService.saveProductImages(multipartFiles);
        Product variantOf = getVariantOf(createProductRequest.variantOfId());

        Product newProduct = ProductMapper.toProduct(createProductRequest, size, category, images, variantOf);

        return ProductMapper.toProductResponse( productRepository.save(newProduct) );
    }

    @Override
    public Product findProductByIdOrThrowException(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product with id " + productId + " does not exist."));
    }

    @Override
    public void deleteProduct(UUID productId){
        Product product = findProductByIdOrThrowException(productId);

        throwExceptionIfProductHasVariants(product);
        ifProductIsNotDeletedThrowException(product);

        product.setDeleted(true);
        productRepository.save(product);
    }

    private void throwExceptionIfProductHasVariants(Product product) {
        if (productRepository.existsByVariantOfAndDeletedFalse(product)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with name " + product.getName() + " has variants and it can't be deleted.");
        }
    }

    private void ifProductIsNotDeletedThrowException(Product product){
        if (product.isDeleted()){
            throw new IllegalStateException("Product with id " + product.getProductId() + " is already deleted.");
        }
    }

    private Product getVariantOf(UUID variantOfId) {
        return variantOfId != null ? findProductByIdOrThrowException(variantOfId) : null;
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest, MultipartFile[] multipartFiles) {
        Product product = this.findProductByIdOrThrowException(productId);
        Category category = categoryService.findCategoryByIdOrThrowException(updateProductRequest.categoryId());

        ifVariantOfExistsUpdateProductVariantOf(updateProductRequest.variantOfId(), product);
        ifImagesExistsUpdateProductImagesURLs(multipartFiles, product);

        product.setName(updateProductRequest.name());
        product.setDescription(updateProductRequest.description());
        product.setPrice(updateProductRequest.price());
        product.setCategory(category);

        return ProductMapper.toProductResponse( productRepository.save(product) );
    }

    @Override
    public ProductResponse restoreProduct(UUID productId) {
        Product product = findProductByIdOrThrowException(productId);
        throwExceptionIfProductIsNotDeleted(product);
        product.setDeleted(false);
        return ProductMapper.toProductResponse( productRepository.save(product) );
    }

    @Override
    public ProductResponse getProduct(UUID productId) {
        return ProductMapper.toProductResponse(findProductByIdOrThrowException(productId));
    }

    @Override
    public List<ProductResponse> getProductVariants(UUID productId) {
        Product product = findProductByIdOrThrowException(productId);
        return productRepository.findProductByVariantOfAndDeletedFalse(product).stream()
                .map(ProductMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> getProducts(int page, int pageSize, String name) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findProductsByDeletedIsFalseAndVariantOfIsNullAndNameContainingIgnoreCase(name, pageable)
                .map(ProductMapper::toProductResponse);
    }

    public void ifVariantOfExistsUpdateProductVariantOf(UUID variantOfId, Product product){
        if(variantOfId != null && !variantOfId.equals(product.getVariantOf().getProductId())){
            Product variantOf = this.getVariantOf(variantOfId);
            product.setVariantOf(variantOf);
        }
    }

    public void ifImagesExistsUpdateProductImagesURLs(MultipartFile[] multipartFiles, Product product) {
        if (multipartFiles != null) {
            List<ProductImage> images = productImageService.saveProductImages(multipartFiles);
            product.setImages(images);
        }
    }

    private void throwExceptionIfProductIsNotDeleted(Product product) {
        if (!product.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with id " + product.getProductId() + " is not deleted.");
        }
    }

}