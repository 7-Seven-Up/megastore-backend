package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.services.interfaces.IEmailService;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.UserMapper;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

  @Override
  public void sendEmailToRecoverPassword(String email) {
    User user = findUserByEmailOrThrowException(email);

    generateRecoverPasswordToken(user);

    userRepository.save(user);

    String recoverPasswordURL = frontendURL + "/auth/recover-password?token=" + user.getRecoverPasswordToken();

    String emailContent = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <title>Password Recovery</title>\n"
            + "    <style>\n"
            + "        body { font-family: Arial, sans-serif; }\n"
            + "        .container { width: 80%; margin: 0 auto; }\n"
            + "        .header { background-color: #f4f4f4; padding: 20px; text-align: center; }\n"
            + "        .content { padding: 20px; }\n"
            + "        .footer { background-color: #f4f4f4; padding: 10px; text-align: center; font-size: 12px; }\n"
            + "        .button {\n"
            + "            display: inline-block;\n"
            + "            padding: 10px 20px;\n"
            + "            font-size: 16px;\n"
            + "            color: #fff;\n"
            + "            background-color: #1ae866;\n"
            + "            text-decoration: none;\n"
            + "            border-radius: 5px;\n"
            + "            text-align: center;\n"
            + "        }\n"
            + "        .button:hover {\n"
            + "            background-color: #15d67c;\n"
            + "        }\n"
            + "    </style>\n"
            + "</head>\n"
            + "<body>\n"
            + "    <div class=\"container\">\n"
            + "        <div class=\"header\">\n"
            + "            <h1>Password Recovery</h1>\n"
            + "        </div>\n"
            + "        <div class=\"content\">\n"
            + "            <p>Hello " + user.getUsername() + ",</p>\n"
            + "            <p>We received a request to reset your password. Click the button below to create a new password:</p>\n"
            + "            <p style=\"display: flex; flex: content; justify-content: center;\"><a href=\" " + recoverPasswordURL + "\" class=\"button\">Reset Password</a></p>\n"
            + "            <p>If you did not request this change, please ignore this email.</p>\n"
            + "            <p>Best regards,<br>The Support Team</p>\n"
            + "        </div>\n"
            + "        <div class=\"footer\">\n"
            + "            <p>Megastore, Villa Maria, Cordoba, Argentina</p>\n"
            + "        </div>\n"
            + "    </div>\n"
            + "</body>\n"
            + "</html>";
    emailService.sendEmail(email, "Recover Password", emailContent);
  }

  private User findUserByEmailOrThrowException(String email) {
    return userRepository.findByEmailAndDeletedIsFalse(email).orElseThrow(() -> new RuntimeException("User with email " + email +" does not exists."));
  }

  @Override
  public void recoverPassword(UUID userId, String password, String confirmPassword, UUID recoverPasswordToken) {
    User user = findUserByIdOrThrowException(userId);
    if(isRecoverPasswordTokenExpired(user)){
      throw new IllegalArgumentException("Recover Token has expired");
    }
    ifTokenIsNotTheSameThrowException(recoverPasswordToken, user);
    ifPasswordIsNotStrongerThrowException(password);
    ifPasswordsAreNotEqualsThrowException(password, confirmPassword);
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
  }

  private void ifTokenIsNotTheSameThrowException(UUID recoverPasswordToken, User user) {
    if (!user.getRecoverPasswordToken().equals(recoverPasswordToken))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The token is not the same");
  }

  private void ifPasswordIsNotStrongerThrowException(String password) {
    String regexStrongPassword = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!¡¿?$%&#@_-])[A-Za-z0-9!¡¿?$%&#@_-]+$";
    if (!password.matches(regexStrongPassword))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not stronger");
  }

  private void ifPasswordsAreNotEqualsThrowException(String password, String confirmPassword) {
    if (!password.equals(confirmPassword))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
  }

  private User findUserByIdOrThrowException(UUID userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User with id " + userId + "does not exist"));
  }

  private void generateRecoverPasswordToken(User user){
    user.setRecoverPasswordToken(UUID.randomUUID());
    user.setRecoverTokenExpirationDate(LocalDateTime.now().plusHours(24));
  }

  private boolean isRecoverPasswordTokenExpired(User user){
    return user.getRecoverTokenExpirationDate().isBefore(LocalDateTime.now());
  }
}
