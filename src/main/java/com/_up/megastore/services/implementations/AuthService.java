package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.services.interfaces.IAuthService;
import com._up.megastore.services.interfaces.IUserService;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

  private final IUserService userService;

  public AuthService(IUserService userService) {
    this.userService = userService;
  }

  @Override
  public void signUp(SignUpRequest signUpRequest) {
    userService.saveUser(signUpRequest);
  }
}
