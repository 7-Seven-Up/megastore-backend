package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.AuthRequest;
import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.controllers.responses.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/auth")
public interface IAuthController {

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  void signUp(@RequestBody @Valid SignUpRequest signUpRequest);

  @PostMapping("/signin")
  @ResponseStatus(HttpStatus.OK)
  AuthResponse signIn(@RequestBody @Valid AuthRequest authRequest);
}
