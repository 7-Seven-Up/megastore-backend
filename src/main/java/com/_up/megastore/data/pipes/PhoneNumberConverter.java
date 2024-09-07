package com._up.megastore.data.pipes;

import com._up.megastore.data.utilities.PhoneNumberConversion;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PhoneNumberConverter implements AttributeConverter<String, String> {

  @Override
  public String convertToDatabaseColumn(String phoneNumber) {
    return PhoneNumberConversion.convertPhoneNumber(phoneNumber);
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    return dbData;
  }
}
