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
                .map(image -> {
                    throwExceptionIfImageSizeIsExceeding(image);
                    throwExceptionIfImageNameAlreadyExists(getImageFilename(image));
                    return saveProductImage(image);
                })
                .toList();
    }

    private ProductImage saveProductImage(MultipartFile multipartFile) {
        String name = getImageFilename(multipartFile);
        String imageURL = fileUploadService.uploadImage(multipartFile);
        return productImageRepository.save(new ProductImage(name, imageURL));
    }

    private String getImageFilename(MultipartFile multipartFile) {
        return Optional.ofNullable(multipartFile.getOriginalFilename())
                .filter(name -> !name.isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The file must have a name."));
    }

    private void throwExceptionIfImageSizeIsExceeding(MultipartFile multipartFile) {
        if (multipartFile.getSize() > 1_000_000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The file size exceeds 1 MB");
        }
    }

    private void throwExceptionIfImageNameAlreadyExists(String name) {
        if (productImageRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product image with name " + name + " already exists.");
        }
    }

}