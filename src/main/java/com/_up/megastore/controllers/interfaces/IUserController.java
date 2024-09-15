package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.ActivateUserRequest;
import com._up.megastore.controllers.requests.RecoverPasswordRequest;
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
  void sendEmailToRecoverPassword(@RequestParam String email);

  @PostMapping("/{userId}/recover-password") @ResponseStatus(HttpStatus.NO_CONTENT)
  void recoverPassword(@PathVariable UUID userId, @RequestBody RecoverPasswordRequest recoverPasswordRequest);
}
