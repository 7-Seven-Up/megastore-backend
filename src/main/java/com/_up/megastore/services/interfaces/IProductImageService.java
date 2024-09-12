package com._up.megastore.services.interfaces;

import com._up.megastore.data.model.ProductImage;
import org.springframework.web.multipart.MultipartFile;

public interface IProductImageService {
    ProductImage saveProductImage(MultipartFile multipartFile);
}