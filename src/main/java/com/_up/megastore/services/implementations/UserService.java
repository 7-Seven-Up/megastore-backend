package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.services.interfaces.IEmailService;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.util.UUID.randomUUID;

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
    UUID activationTokenUUID = generateActivationToken(newUser);
    sendActivationEmail(newUser,activationTokenUUID);
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
    if(isTokenExpired(user, activationToken)){
      throw new IllegalArgumentException("Token has expired");
    }
    user.setActivated(true);
    userRepository.save(user);
    sendWelcomeEmail(user);
  }

  private User createUser(SignUpRequest createUserRequest) {
    User user = UserMapper.toUser(createUserRequest);
    String passwordEncoded = passwordEncoder.encode(createUserRequest.password());
    user.setPassword(passwordEncoded);
    return user;
  }

  private void sendActivationEmail(User user, UUID activationToken) {
    String activationUrl = frontendURL + "/auth/activate?userId="
        + user.getUserId() + "&activationToken=" + findActivationTokenByTokenUUIDOrThrowException(user, activationToken).getActivationToken();

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
      UUID activationTokenUUID) {
    return userRepository.findByUserIdAndActivatedIsFalse(userId).filter(user -> user.getActivationTokens().stream().anyMatch( activationToken-> activationToken.getActivationToken().equals(activationTokenUUID)))
        .orElseThrow(
            () -> new IllegalArgumentException("Token is invalid or user is already activated"));
  }

  private UUID generateActivationToken(User user){
    Token activationToken = new Token();
    UUID activationTokenUUID = randomUUID();
    activationToken.setActivationToken(activationTokenUUID);
    activationToken.setUser(user);
    List<Token> activationTokens = user.getActivationTokens();
    activationTokens.add(activationToken);
    user.setActivationTokens(activationTokens);
    return activationTokenUUID;
  }

  private Boolean isTokenExpired(User user,UUID activationTokenUUID){
    Token activationToken = findActivationTokenByTokenUUIDOrThrowException(user,activationTokenUUID);
      return activationToken.getTokenExpirationDate().isBefore(LocalDateTime.now());
  }

  private Token findActivationTokenByTokenUUIDOrThrowException(User user,UUID activationTokenUUID){
    return user.getActivationTokens().stream().filter(token->token.getActivationToken().equals(activationTokenUUID)).findFirst()
            .orElseThrow(() -> new NoSuchElementException());
  }
}
