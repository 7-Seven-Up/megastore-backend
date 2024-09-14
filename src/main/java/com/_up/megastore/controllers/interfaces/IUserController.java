package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.ActivateUserRequest;
import jakarta.validation.Valid;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
public interface IUserController {

  @PostMapping("/{userId}/activate")
  void activateUser(@PathVariable UUID userId,
      @RequestBody @Valid ActivateUserRequest activateUserRequest);

  @PostMapping("/recover-password/send-email")
  void sendEmailToRecoverPassword(@RequestParam String email);

  @PostMapping("/{userId}/recover-password")
  void recoverPassword(@PathVariable UUID userId, @RequestParam String newPassword, @RequestParam UUID recoverPasswordToken);

}
