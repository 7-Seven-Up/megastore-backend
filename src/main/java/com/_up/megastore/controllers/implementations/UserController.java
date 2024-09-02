package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.IUserController;
import com._up.megastore.controllers.requests.CreateUserRequest;
import com._up.megastore.controllers.responses.UserResponse;
import com._up.megastore.services.interfaces.IUserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController {

  private final IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @Override
  public UserResponse saveUser(CreateUserRequest createUserRequest) {
    return userService.saveUser(createUserRequest);
  }
}
