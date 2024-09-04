package com._up.megastore.services.interfaces;

import com._up.megastore.data.model.User;

public interface IUserService {

  void saveUser(User newUser);

  void ifEmailAlreadyExistsThrowException(String email);

  void ifUsernameAlreadyExistsThrowException(String username);
}
