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

import java.util.NoSuchElementException;

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
  public User findUserByUsernameOrThrowException(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("User with username "+username+" does not exist."));
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

  private User findUserOrThrowException(UUID userId) {
    return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
  }

  @Override
  public void sendEmailToRecoverPassword(UUID userId, String email) {
    if (!userRepository.existsById(userId))
      throw new RuntimeException("User not found");

    User user = findUserOrThrowException(userId);

    if (!userRepository.existsByEmail(email))
      throw new RuntimeException("User with email " + email + " not found");

    // TODO : validate expiration date email with [ table | jwt ]
    String recoverPasswordURL = frontendURL + "/auth/recoverPassword?userId=" + userId;

    String emailContent = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "<meta charset='UTF-8'>"
            + "<title>Password Recovery</title>"
            + "<style>"
            + "body { font-family: Arial, sans-serif; }"
            + ".container { width: 80%; margin: 0 auto; }"
            + ".header { background-color: #f4f4f4; padding: 20px; text-align: center; }"
            + ".content { padding: 20px; }"
            + ".footer { background-color: #f4f4f4; padding: 10px; text-align: center; font-size: 12px; }"
            + ".button {"
            + "display: inline-block;"
            + "padding: 10px 20px;"
            + "font-size: 16px;"
            + "color: #fff;"
            + "background-color: #1a73e8;"
            + "text-decoration: none;"
            + "border-radius: 5px;"
            + "text-align: center;"
            + "}"
            + ".button:hover {"
            + "background-color: #1558d6;"
            + "}"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<div class='container'>"
            + "<div class='header'>"
            + "<h1>Password Recovery</h1>"
            + "</div>"
            + "<div class='content'>"
            + "<p>Hello " + user.getUsername() + ",</p>"
            + "<p>We received a request to reset your password. Click the button below to create a new password:</p>"
            + "<p><a href='" + recoverPasswordURL + "' class='button'>Reset Password</a></p>"
            + "<p>If you did not request this change, please ignore this email.</p>"
            + "<p>Best regards,<br>The Support Team</p>"
            + "</div>"
            + "<div class='footer'>"
            + "<p>Megastore, Villa Maria, Cordoba, Argentina</p>"
            + "</div>"
            + "</div>"
            + "</body>"
            + "</html>";
    emailService.sendEmail(email, "Recover Password", emailContent);
  }

  @Override
  public void recoverPassword(UUID userId, String newPassword) {
    User user = findUserOrThrowException(userId);
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }

}
