package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{productId}/variants")
    @ResponseStatus(HttpStatus.OK)
    List<ProductResponse> getProductVariants(@PathVariable UUID productId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<ProductResponse> getProducts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "15") int pageSize,
                                      @RequestParam(defaultValue = "") String name,
                                      @RequestParam(defaultValue = "") String category);

    @GetMapping("/deleted")
    @ResponseStatus(HttpStatus.OK)
    Page<ProductResponse> getDeletedProducts(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int pageSize);
}