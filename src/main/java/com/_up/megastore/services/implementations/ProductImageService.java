package com._up.megastore.services.implementations;

import com._up.megastore.data.model.ProductImage;
import com._up.megastore.data.repositories.IProductImageRepository;
import com._up.megastore.services.interfaces.IFileUploadService;
import com._up.megastore.services.interfaces.IProductImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageService implements IProductImageService {

    private final IFileUploadService fileUploadService;
    private final IProductImageRepository productImageRepository;

    public ProductImageService(IFileUploadService fileUploadService, IProductImageRepository productImageRepository) {
        this.fileUploadService = fileUploadService;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public List<ProductImage> saveProductImages(MultipartFile[] multipartFiles) {
        return Arrays.stream(multipartFiles)
                .map(this::saveProductImage)
                .collect(Collectors.toList());
    }

    private ProductImage saveProductImage(MultipartFile multipartFile) {
        String imageURL = fileUploadService.uploadImage(multipartFile);
        return productImageRepository.save( new ProductImage(imageURL) );
    }

}