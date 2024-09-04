package com._up.megastore.services.implementations;

import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.services.interfaces.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private final IUserRepository userRepository;

  public UserService(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void saveUser(User newUser) {
    userRepository.save(newUser);
  }

  @Override
  public void ifEmailAlreadyExistsThrowException(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already exists");
    }
  }

  @Override
  public void ifUsernameAlreadyExistsThrowException(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("Username already exists");
    }
  }
}
