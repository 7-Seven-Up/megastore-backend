package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.ActivateUserRequest;
import com._up.megastore.controllers.requests.RecoverPasswordRequest;
import com._up.megastore.controllers.requests.SendEmailRequest;
import jakarta.validation.Valid;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
public interface IUserController {

  @PostMapping("/{userId}/activate")
  void activateUser(@PathVariable UUID userId,
      @RequestBody @Valid ActivateUserRequest activateUserRequest);

  @PostMapping("/recover-password/send-email") @ResponseStatus(HttpStatus.NO_CONTENT)
  void sendEmailToRecoverPassword(@RequestBody SendEmailRequest sendEmailRequest);

  @PostMapping("/recover-password") @ResponseStatus(HttpStatus.NO_CONTENT)
  void recoverPassword(@RequestBody RecoverPasswordRequest recoverPasswordRequest);

  @PostMapping("/resend-activation-email")
  void resendActivationEmail(@RequestBody SendEmailRequest sendEmailRequest);
}