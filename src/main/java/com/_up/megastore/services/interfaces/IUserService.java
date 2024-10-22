package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.RecoverPasswordRequest;
import com._up.megastore.controllers.requests.SendEmailRequest;
import com._up.megastore.controllers.requests.SendNewActivationTokenRequest;
import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.User;
import java.util.UUID;

public interface IUserService {

  void saveUser(SignUpRequest newUser);

  User findUserByUsernameOrThrowException(String username);

  User findUserByIdOrThrowException(UUID userId);

  void activateUser(UUID userId, UUID activationToken);

  void sendEmailToRecoverPassword(String email);
  void recoverPassword(RecoverPasswordRequest recoverPasswordRequest);

  void resendActivationEmail(SendEmailRequest sendEmailRequest);

  void sendNewActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest);
}