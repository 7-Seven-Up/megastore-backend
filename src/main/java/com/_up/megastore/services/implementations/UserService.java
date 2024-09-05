package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public void saveUser(SignUpRequest createUserRequest) {
    ifEmailAlreadyExistsThrowException(createUserRequest.email());
    ifUsernameAlreadyExistsThrowException(createUserRequest.username());
    User newUser = createUser(createUserRequest);
    userRepository.save(newUser);
  }

  User createUser(SignUpRequest createUserRequest) {
    User user = UserMapper.toUser(createUserRequest);
    String passwordEncoded = passwordEncoder.encode(createUserRequest.password());
    user.setPassword(passwordEncoded);
    return user;
  }

  private void ifEmailAlreadyExistsThrowException(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already exists");
    }
  }

  private void ifUsernameAlreadyExistsThrowException(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("Username already exists");
    }
  }
}
