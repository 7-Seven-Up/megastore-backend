package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.SignUpRequest;

public interface IUserService {

  void saveUser(SignUpRequest newUser);
}
