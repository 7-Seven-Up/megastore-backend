package com._up.megastore.data.pipes;

import com._up.megastore.data.repositories.ISizeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SizeNameValidator implements ConstraintValidator<SizeName, String> {
    private final ISizeRepository sizeRepository;

    public SizeNameValidator(ISizeRepository sizeRepository){
        this.sizeRepository = sizeRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext){
        if (s == null || s.isEmpty()) return true;
        return !sizeRepository.existsByNameAndDeletedIsFalse(s);
    }
}
