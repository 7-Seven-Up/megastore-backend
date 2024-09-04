package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.CreateUserRequest;
import com._up.megastore.controllers.responses.UserResponse;
import com._up.megastore.data.model.User;
import com._up.megastore.data.utilities.PhoneNumberConversion;

public class UserMapper {

  public static UserResponse toUserResponse(User user) {
    return new UserResponse(
        user.getUserId(),
        user.getUsername(),
        user.getFullName(),
        user.getEmail(),
        user.getPhoneNumber(),
        user.getRole()
    );
  }

  public static User toUser(CreateUserRequest createUserRequest) {
    return User.builder()
        .email(createUserRequest.email())
        .fullName(createUserRequest.fullName())
        .password(createUserRequest.password())
        .phoneNumber(
            PhoneNumberConversion.convertPhoneNumber(createUserRequest.phoneNumber())
        )
        .username(createUserRequest.username())
        .build();
  }
}
