package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.services.interfaces.IEmailService;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.UserMapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private final IEmailService emailService;
  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${frontend.url}")
  private String frontendURL;

  public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder,
      IEmailService emailService) {
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public void saveUser(SignUpRequest createUserRequest) {
    ifEmailAlreadyExistsThrowException(createUserRequest.email());
    ifUsernameAlreadyExistsThrowException(createUserRequest.username());
    User newUser = createUser(createUserRequest);
    sendActivationEmail(newUser);
    userRepository.save(newUser);
  }

  @Override
  public void activateUser(UUID userId, UUID activationToken) {
    User user = findUserToActivateOrThrowException(userId, activationToken);
    user.setActivated(true);
    userRepository.save(user);
    sendWelcomeEmail(user);
  }

  User createUser(SignUpRequest createUserRequest) {
    User user = UserMapper.toUser(createUserRequest);
    String passwordEncoded = passwordEncoder.encode(createUserRequest.password());
    user.setPassword(passwordEncoded);
    return user;
  }

  private void sendActivationEmail(User user) {
    String activationUrl = frontendURL + "/auth/activate?userId="
        + user.getUserId() + "&activationToken=" + user.getActivationToken();

    String emailBody = "<table style='width:100%; height:100%;'>"
        + "<tr>"
        + "<td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
        + "<div style='display:inline-block;'>"
        + "<h1>Welcome to Megastore</h1>"
        + "<h3>Hey " + user.getFullName() + "!</h3>"
        + "<h4>Thanks for joining us!</h4>"
        + "<p>Click the link below to activate your account</p>"
        + "<a href=\""
        + activationUrl
        + "\">Activate account</a>"
        + "</div>"
        + "</td>"
        + "</tr>"
        + "</table>";

    emailService.sendEmail(user.getEmail(), "Account activation", emailBody);
  }

  private void sendWelcomeEmail(User user) {
    String emailBody = "<table style='width:100%; height:100%;'>"
        + "<tr><td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
        + "<div style='display:inline-block;'>"
        + "<h1>Welcome to Megastore</h1>"
        + "<h3>Hey " + user.getFullName() + "!</h3>"
        + "<h4>Your account has been activated.</h4>"
        + "<p>Enjoy shopping with us!</p>"
        + "</div></td></tr></table>";

    emailService.sendEmail(user.getEmail(), "Welcome to Megastore", emailBody);
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

  private User findUserToActivateOrThrowException(UUID userId,
      UUID activationToken) {
    return userRepository.findByUserIdAndActivationTokenAndActivatedIsFalse(userId,
            activationToken)
        .orElseThrow(
            () -> new IllegalArgumentException("Token is invalid or user is already activated"));
  }

}
