package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateUserRequest;
import com._up.megastore.controllers.responses.UserResponse;

public interface IUserService {

  UserResponse saveUser(CreateUserRequest createUserRequest);

}
