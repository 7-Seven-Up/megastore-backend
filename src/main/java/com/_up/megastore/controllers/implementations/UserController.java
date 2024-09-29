package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.IUserController;
import com._up.megastore.controllers.requests.ActivateUserRequest;
import com._up.megastore.controllers.requests.RecoverPasswordRequest;
import com._up.megastore.controllers.requests.SendEmailRequest;
import com._up.megastore.controllers.requests.SendNewActivationTokenRequest;
import com._up.megastore.services.implementations.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController implements IUserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void activateUser(UUID userId, ActivateUserRequest activateUserRequest) {
    userService.activateUser(userId, activateUserRequest.activationToken());
  }

  @Override
  public void sendEmailToRecoverPassword(SendEmailRequest request) {
    userService.sendEmailToRecoverPassword(request.email());
  }

  @Override
  public void recoverPassword(RecoverPasswordRequest recoverPasswordRequest) {
    userService.recoverPassword( recoverPasswordRequest);
  }

  @Override
  public void resendActivationEmail(SendEmailRequest sendEmailRequest) {
    userService.resendActivationEmail(sendEmailRequest);
  }

  @Override
  public void sendNewActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest) {
    userService.sendNewActivationToken(sendNewActivationTokenRequest);
  }
}