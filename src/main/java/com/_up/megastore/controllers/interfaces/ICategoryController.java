package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.requests.UpdateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/v1/categories")
public interface ICategoryController {

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    CategoryResponse saveCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest);

    @PostMapping(value = "/{categoryId}/restore") @ResponseStatus(HttpStatus.OK)
    CategoryResponse restoreCategory(@PathVariable UUID categoryId);

    @PutMapping (value = "/{categoryId}") @ResponseStatus(HttpStatus.OK)
    CategoryResponse updateCategory(@PathVariable UUID categoryId, @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest);

}