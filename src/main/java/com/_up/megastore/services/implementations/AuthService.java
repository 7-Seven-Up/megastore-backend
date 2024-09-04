package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.User;
import com._up.megastore.services.interfaces.IAuthService;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

  private final IUserService userService;
  private final PasswordEncoder passwordEncoder;

  public AuthService(IUserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void signUp(SignUpRequest signUpRequest) {
    validateSignUpRequest(signUpRequest);
    User user = createUser(signUpRequest);
    userService.saveUser(user);
  }

  public void validateSignUpRequest(SignUpRequest createUserRequest) {
    userService.ifEmailAlreadyExistsThrowException(createUserRequest.email());
    userService.ifUsernameAlreadyExistsThrowException(createUserRequest.username());
  }

  private User createUser(SignUpRequest createUserRequest) {
    User user = UserMapper.toUser(createUserRequest);
    String passwordEncoded = passwordEncoder.encode(createUserRequest.password());
    user.setPassword(passwordEncoded);
    return user;
  }
}
