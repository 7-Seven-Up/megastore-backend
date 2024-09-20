package com._up.megastore.services.implementations;

import com._up.megastore.data.model.ProductImage;
import com._up.megastore.data.repositories.IProductImageRepository;
import com._up.megastore.services.interfaces.IFileUploadService;
import com._up.megastore.services.interfaces.IProductImageService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        validateDuplicateImageNames(multipartFiles);
        return Arrays.stream(multipartFiles)
                .map(this::saveProductImage)
                .collect(Collectors.toList());
    }

    private ProductImage saveProductImage(MultipartFile multipartFile) {
        String name = getImageFilename(multipartFile);
        String imageURL = fileUploadService.uploadImage(multipartFile);
        return productImageRepository.save( new ProductImage(name, imageURL) );
    }

    private String getImageFilename(MultipartFile multipartFile) {
        return Optional.ofNullable(multipartFile.getOriginalFilename())
                .filter(name -> !name.isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The file must have a name."));
    }

    private void validateDuplicateImageNames(MultipartFile[] multipartFiles) {
        Arrays.stream(multipartFiles)
                .forEach(image -> ifImageNameAlreadyExistsThrowException( getImageFilename(image) ));
    }

    private void ifImageNameAlreadyExistsThrowException(String name) {
        if (productImageRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product image with name "+name+" already exists.");
        }
    }

}