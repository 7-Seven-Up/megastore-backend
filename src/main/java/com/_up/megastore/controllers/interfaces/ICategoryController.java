package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.requests.UpdateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;



@RequestMapping("/api/v1/categories")
public interface ICategoryController {

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    CategoryResponse saveCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest);


    @GetMapping(value = "/{categoryId}") @ResponseStatus(HttpStatus.OK)
    CategoryResponse readCategory(@PathVariable UUID categoryId);

    @GetMapping @ResponseStatus(HttpStatus.OK)
    Page<CategoryResponse> readAllCategories(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "15") int pageSize,
                                             @RequestParam(defaultValue = "") String name);

    @GetMapping(value = "/deleted")
    @ResponseStatus(HttpStatus.OK)
    Page<CategoryResponse> readDeletedCategories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int pageSize
    );

    @DeleteMapping(value = "/{categoryId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable UUID categoryId);

    @PostMapping(value = "/{categoryId}/restore") @ResponseStatus(HttpStatus.OK)
    CategoryResponse restoreCategory(@PathVariable UUID categoryId);

    @PutMapping (value = "/{categoryId}") @ResponseStatus(HttpStatus.OK)
    CategoryResponse updateCategory(@PathVariable UUID categoryId, @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest);

}