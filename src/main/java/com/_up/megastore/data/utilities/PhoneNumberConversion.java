package com._up.megastore.data.utilities;

import com._up.megastore.exception.custom_exceptions.PhoneNumberParseException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneNumberConversion {

    private PhoneNumberConversion() {}

  /**
   * Converts a phone number string to the E.164 format.
   *
   * @param phoneNumber the phone number string to be converted
   * @return the phone number in E.164 format
   * @throws RuntimeException if the phone number cannot be parsed
   */
  public static String convertPhoneNumber(String phoneNumber) {
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    try {
      PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "AR");
      return phoneUtil.format(numberProto, PhoneNumberFormat.E164);
    } catch (NumberParseException e) {
      throw new PhoneNumberParseException("Failed to convert phone number: " + phoneNumber);
    }
  }
}
