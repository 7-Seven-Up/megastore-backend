package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ISizeService sizeService;
    private final ICategoryService categoryService;
    private final IFileUploadService fileUploadService;

    public ProductService(IProductRepository productRepository, ISizeService sizeService, ICategoryService categoryService, IFileUploadService fileUploadService) {
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
    public Product findProductByIdOrThrowException(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product with id " + productId + " does not exist."));
    }

    private String saveProductImage(MultipartFile multipartFile) {
        return fileUploadService.uploadImage(multipartFile);
    }

    private Product getVariantOf(UUID variantOfId) {
        return variantOfId != null ? findProductByIdOrThrowException(variantOfId) : null;
    }

    @Override
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest, MultipartFile multipartFile){
        Product product = this.findProductByIdOrThrowException(productId);
        Category category = categoryService.findCategoryByIdOrThrowException(updateProductRequest.categoryId());
        ifVariantOfExistsUpdateProductVariantOf(updateProductRequest.variantOfId(), product);
        ifImageURLExistsUpdateProductImageURL(multipartFile, product);
        product.setName(updateProductRequest.name());
        product.setDescription(updateProductRequest.description());
        product.setPrice(updateProductRequest.price());
        product.setCategory(category);

        return ProductMapper.toProductResponse( productRepository.save(product) );
    }

    public void ifVariantOfExistsUpdateProductVariantOf(UUID variantOfId, Product product){
        if(variantOfId != null && !variantOfId.equals(product.getVariantOf().getProductId())){
            Product variantOf = this.getVariantOf(variantOfId);
            product.setVariantOf(variantOf);
        }
    }

    public void ifImageURLExistsUpdateProductImageURL(MultipartFile multipartFile, Product product){
        if(multipartFile != null){
            String imageURL = saveProductImage(multipartFile);
            product.setImageURL(imageURL);
        }
    }
}