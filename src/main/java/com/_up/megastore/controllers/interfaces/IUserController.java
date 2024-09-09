package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.ActivateUserRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/users")
public interface IUserController {

  @PostMapping("/{userId}/activate")
  void activateUser(@PathVariable UUID userId,
      @RequestBody @Valid ActivateUserRequest activateUserRequest);

}
