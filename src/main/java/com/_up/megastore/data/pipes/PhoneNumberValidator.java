package com._up.megastore.data.pipes;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements
    ConstraintValidator<com._up.megastore.data.pipes.PhoneNumber, String> {

  private String isoCode;

  @Override
  public void initialize(com._up.megastore.data.pipes.PhoneNumber phoneNumberConstraint) {
    this.isoCode = phoneNumberConstraint.isoCode();
  }

  @Override
  public boolean isValid(String phoneNumber,
      ConstraintValidatorContext constraintValidatorContext) {
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    if (phoneNumber == null) {
      return true;
    }

    try {
      PhoneNumber arNumberProto = phoneUtil.parse(phoneNumber, isoCode);
      return phoneUtil.isValidNumberForRegion(arNumberProto, isoCode);
    } catch (NumberParseException e) {
      return false;
    }
  }
}

