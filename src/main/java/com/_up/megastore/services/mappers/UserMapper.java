package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.User;

public class UserMapper {

  public static User toUser(SignUpRequest createUserRequest, String convertedPhoneNumber) {
    return User.builder()
        .email(createUserRequest.email())
        .fullName(createUserRequest.fullName())
        .password(createUserRequest.password())
        .phoneNumber(convertedPhoneNumber)
        .username(createUserRequest.username())
        .build();
  }
}
