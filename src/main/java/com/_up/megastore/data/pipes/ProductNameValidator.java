package com._up.megastore.data.pipes;

import com._up.megastore.data.repositories.IProductRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductNameValidator implements ConstraintValidator<ProductName, String> {
    private final IProductRepository productRepository;

    public ProductNameValidator(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) return true;
        return !productRepository.existsByNameAndDeletedIsFalse(s);
    }

}