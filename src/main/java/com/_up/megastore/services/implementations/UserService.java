package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateUserRequest;
import com._up.megastore.controllers.responses.UserResponse;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.services.interfaces.IMailSenderService;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private final IMailSenderService mailSenderService;
  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder,
      IMailSenderService mailSenderService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.mailSenderService = mailSenderService;
  }

  @Override
  public UserResponse saveUser(CreateUserRequest createUserRequest) {
    validateRequest(createUserRequest);

    User user = UserMapper.toUser(createUserRequest);
    String passwordEncoded = passwordEncoder.encode(createUserRequest.password());
    user.setPassword(passwordEncoded);
    mailSenderService.sendWelcomeEmail(user);

    return UserMapper.toUserResponse(userRepository.save(user));
  }

  private void validateRequest(CreateUserRequest createUserRequest) {
    checkIfEmailExistsOrThrowException(createUserRequest.email());
    checkIfUsernameExistsOrThrowException(createUserRequest.username());
  }

  private void checkIfEmailExistsOrThrowException(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already exists");
    }
  }

  private void checkIfUsernameExistsOrThrowException(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("Username already exists");
    }
  }
}
