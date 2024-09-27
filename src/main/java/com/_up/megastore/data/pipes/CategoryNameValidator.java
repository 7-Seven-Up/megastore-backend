package com._up.megastore.data.pipes;

import com._up.megastore.data.repositories.ICategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryNameValidator implements ConstraintValidator<CategoryName, String> {
    private final ICategoryRepository categoryRepository;

    public CategoryNameValidator(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext){
        if (s == null || s.isEmpty()) return true;
        return !categoryRepository.existsByNameAndDeletedIsFalse(s);
    }
}
