package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.ITokenRepository;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.services.interfaces.IEmailService;
import com._up.megastore.services.interfaces.ITokenService;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.UserMapper;
import java.time.LocalDateTime;
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
  private final ITokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final ITokenService tokenService;
  @Value("${frontend.url}")
  private String frontendURL;

  public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder,
      IEmailService emailService, ITokenRepository tokenRepository, ITokenService iTokenService) {
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.tokenRepository = tokenRepository;
    this.tokenService = iTokenService;
  }

  @Override
  public void saveUser(SignUpRequest createUserRequest) {
    ifEmailAlreadyExistsThrowException(createUserRequest.email());
    ifUsernameAlreadyExistsThrowException(createUserRequest.username());
    User newUser = createUser(createUserRequest);
    userRepository.save(newUser);
    UUID activationTokenUUID = generateActivationToken(newUser);
    sendActivationEmail(newUser,activationTokenUUID);
  }

  @Override
  public User findUserByUsernameOrThrowException(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with username " + username + " does not exist."));
  }

  @Override
  public void activateUser(UUID userId, UUID activationToken) {
    User user = findUserToActivateOrThrowException(activationToken);
    if(isTokenExpired(activationToken)){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Token has expired");
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
        + user.getUserId() + "&activationToken=" + activationToken;

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

  private User findUserToActivateOrThrowException(UUID activationTokenUUID) {
    UUID userUUID = tokenService.findUser(activationTokenUUID);
    return userRepository.findByUserIdAndActivatedIsFalse(userUUID)
            .orElseThrow(() -> new IllegalArgumentException("Token is invalid or user is already activate."));

  }

  private UUID generateActivationToken(User user){
    return tokenService.saveToken(user).getTokenId();
  }

  private Boolean isTokenExpired(UUID activationTokenUUID){
    Token activationToken = findActivationTokenByTokenUUIDOrThrowException(activationTokenUUID);
      return activationToken.getTokenExpirationDate().isBefore(LocalDateTime.now());
  }

  private Token findActivationTokenByTokenUUIDOrThrowException(UUID activationTokenUUID){
    return tokenRepository.findById(activationTokenUUID)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token with UUID " + activationTokenUUID + " does not exist."));
  }
}
