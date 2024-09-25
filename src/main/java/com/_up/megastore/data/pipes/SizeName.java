package com._up.megastore.data.pipes;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SizeNameValidator.class)
public @interface SizeName {

    String message() default "Size name already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
