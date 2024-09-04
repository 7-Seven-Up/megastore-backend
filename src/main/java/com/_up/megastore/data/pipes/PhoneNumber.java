package com._up.megastore.data.pipes;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PhoneNumber {

  String isoCode() default "AR";

  String message() default "Phone number is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD})
  @interface List {

    PhoneNumber[] value();
  }
}