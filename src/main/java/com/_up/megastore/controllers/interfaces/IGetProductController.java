package com._up.megastore.controllers.interfaces;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com._up.megastore.controllers.responses.ProductResponse;

@RequestMapping("api/v1/products")
public interface IGetProductController {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductResponse findProductById(UUID id);
}
