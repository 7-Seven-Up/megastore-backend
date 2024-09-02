package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateUserRequest;
import com._up.megastore.controllers.responses.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/users")
public interface IUserController {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  UserResponse saveUser(@RequestBody @Valid CreateUserRequest createUserRequest);

}
