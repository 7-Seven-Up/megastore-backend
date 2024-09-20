package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.IUserController;
import com._up.megastore.controllers.requests.ActivateUserRequest;
import com._up.megastore.controllers.requests.RecoverPasswordRequest;
import com._up.megastore.services.implementations.UserService;

import java.util.UUID;
import org.springframework.web.bind.annotation.RestController;

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
  public void sendEmailToRecoverPassword(String email) {
    userService.sendEmailToRecoverPassword(email);
  }

  @Override
  public void recoverPassword(UUID userId, RecoverPasswordRequest recoverPasswordRequest) {
    userService.recoverPassword(userId, recoverPasswordRequest);
  }
}
