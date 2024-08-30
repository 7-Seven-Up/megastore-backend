package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.responses.ImageResponse;
import com._up.megastore.controllers.responses.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/products")
public interface IProductController {

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    ProductResponse saveProduct(@RequestBody CreateProductRequest createProductRequest);

    @PostMapping(value = "/images", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}) @ResponseStatus(HttpStatus.CREATED)
    ImageResponse saveProductImage(@ModelAttribute MultipartFile multipartFile);

}