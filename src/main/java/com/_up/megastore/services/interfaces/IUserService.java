package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.User;
import java.util.UUID;

public interface IUserService {

  void saveUser(SignUpRequest newUser);

  User findUserByUsernameOrThrowException(String username);

  void activateUser(UUID userId, UUID activationToken);

  void sendEmailToRecoverPassword(String email);
  void recoverPassword(UUID userId, String newPassword, UUID recoverPasswordToken);
}
