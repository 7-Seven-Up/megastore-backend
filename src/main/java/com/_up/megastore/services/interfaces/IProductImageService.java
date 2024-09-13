package com._up.megastore.services.interfaces;

import com._up.megastore.data.model.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductImageService {
    List<ProductImage> saveProductImages(MultipartFile[] multipartFiles);
}