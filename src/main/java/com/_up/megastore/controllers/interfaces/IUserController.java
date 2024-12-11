package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.ActivateUserRequest;
import com._up.megastore.controllers.requests.RecoverPasswordRequest;
import com._up.megastore.controllers.requests.SendEmailRequest;
import com._up.megastore.controllers.requests.SendNewActivationTokenRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

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

  @PostMapping("/send-new-activation-token")
  void sendNewActivationToken(@RequestBody SendNewActivationTokenRequest sendNewActivationTokenRequest);

  @GetMapping("/{username}/orders")
  @ResponseStatus(HttpStatus.OK)
  OrderResponse[] getOrders(@PathVariable String username);
}