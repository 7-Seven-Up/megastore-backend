package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/categories")
public interface ICategoryController {

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    CategoryResponse saveCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest);

    @GetMapping @ResponseStatus(HttpStatus.OK)
    CategoryResponse readCategory(@PathVariable UUID categoryId);
}