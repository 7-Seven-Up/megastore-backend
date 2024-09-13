package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
            @RequestPart MultipartFile[] multipartFiles
    );

    @PatchMapping(value = "/{productId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    ProductResponse updateProduct(
            @PathVariable UUID productId,
            @RequestPart @Valid UpdateProductRequest updateProductRequest,
            @RequestPart @Nullable MultipartFile[] multipartFiles
    );

    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(
            @PathVariable UUID productId
    );

    @PostMapping("/{productId}/restore")
    @ResponseStatus(HttpStatus.OK)
    ProductResponse restoreProduct(@PathVariable UUID productId);

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    ProductResponse getProduct(@PathVariable UUID productId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<ProductResponse> getProducts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "15") int pageSize,
                                      @RequestParam(defaultValue = "") String name);
}