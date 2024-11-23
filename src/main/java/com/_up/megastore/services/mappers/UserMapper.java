package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.User;

public class UserMapper {

    private UserMapper() {}

  public static User toUser(SignUpRequest createUserRequest) {
    return User.builder()
        .email(createUserRequest.email())
        .fullName(createUserRequest.fullName())
        .password(createUserRequest.password())
        .phoneNumber(createUserRequest.phoneNumber())
        .username(createUserRequest.username())
        .build();
  }
}
