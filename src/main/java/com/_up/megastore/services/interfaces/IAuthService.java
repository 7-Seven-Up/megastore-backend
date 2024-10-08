package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.controllers.responses.AuthResponse;

public interface IAuthService {

  void signUp(SignUpRequest signUpRequest);

  AuthResponse signIn(String credentials);

}