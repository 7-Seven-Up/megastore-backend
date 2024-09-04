package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequestMapping("/api/v1/products")
public interface IProductController {

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponse saveProduct(
            @RequestPart @Valid CreateProductRequest createProductRequest,
            @RequestPart MultipartFile multipartFile
    );

    @PatchMapping(value = "/{productId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    ProductResponse updateProduct(
            @PathVariable UUID productId,
            @RequestPart @Valid UpdateProductRequest updateProductRequest,
            @RequestPart @Nullable MultipartFile multipartFile
    );

    @PatchMapping(value = "/{productId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    ProductResponse deleteProduct(
            @PathVariable UUID productId
    );

}