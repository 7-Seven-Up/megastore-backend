package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductResponse findProductById(@PathVariable UUID id);

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Page<ProductResponse> findProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "") String name
    );
}