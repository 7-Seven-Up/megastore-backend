package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.SignUpRequest;
import java.util.UUID;

public interface IUserService {

  void saveUser(SignUpRequest newUser);

  void activateUser(UUID userId, UUID activationToken);

  void sendEmailToRecoverPassword(UUID userId, String email);
  void recoverPassword(UUID userId, String newPassword);
}
