package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/categories")
public interface ICategoryController {

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    CategoryResponse saveCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest);

}