package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.IAuthController;
import com._up.megastore.controllers.requests.AuthRequest;
import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.controllers.responses.AuthResponse;
import com._up.megastore.services.interfaces.IAuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements IAuthController {

  private final IAuthService authService;

  public AuthController(IAuthService authService) {
    this.authService = authService;
  }

  @Override
  public void signUp(SignUpRequest signUpRequest) {
    this.authService.signUp(signUpRequest);
  }

  @Override
  public AuthResponse signIn(AuthRequest authRequest) {
    return this.authService.signIn(authRequest);
  }
}
