package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.RecoverPasswordRequest;
import com._up.megastore.controllers.requests.SendEmailRequest;
import com._up.megastore.controllers.requests.SendNewActivationTokenRequest;
import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.services.interfaces.IEmailService;
import com._up.megastore.services.interfaces.ITokenService;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.UserMapper;
import com._up.megastore.utils.EmailBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IUserService {

  private final IEmailService emailService;
  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ITokenService tokenService;
  private final EmailBuilder emailBuilder;

  public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder,
                     IEmailService emailService, ITokenService tokenService, EmailBuilder emailBuilder) {
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.tokenService = tokenService;
    this.emailBuilder = emailBuilder;
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
  public User findUserByIdOrThrowException(UUID userId) {
    return userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with id " + userId + " does not exist."));
  }

  @Override
  public void activateUser(UUID userId, UUID activationToken) {
    throwExceptionIfTokenIsExpired(activationToken);

    User user = findUserToActivateOrThrowException(activationToken);
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
    String emailBody = emailBuilder.buildActivationEmailBody(user, activationToken);
    emailService.sendEmail(user.getEmail(), "Account activation", emailBody);
  }

  private void sendWelcomeEmail(User user) {
    String emailBody = emailBuilder.buildWelcomeEmail(user);
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
    User user = tokenService.findUserByToken(activationTokenUUID);
    ifUserIsActivatedThrowException(user);
    return user;
  }
  private void ifUserIsActivatedThrowException(User user){
    if(user.isActivated()){
      throw new IllegalArgumentException("User is already activated.");
    }
  }

  private UUID generateActivationToken(User user){
    return tokenService.saveToken(user).getTokenId();
  }

  private boolean isTokenExpired(UUID activationTokenUUID){
    Token activationToken = findActivationTokenByTokenUUIDOrThrowException(activationTokenUUID);
      return activationToken.getTokenExpirationDate().isBefore(LocalDateTime.now());
  }

  private Token findActivationTokenByTokenUUIDOrThrowException(UUID activationTokenUUID){
    return tokenService.findTokenByIdOrThrowException(activationTokenUUID);
  }

  @Override
  public void sendEmailToRecoverPassword(String email) {
    User user = findUserByEmailOrThrowException(email);
    Token recoverPasswordToken = tokenService.saveToken(user);
    String emailContent = emailBuilder.buildRecoverPasswordEmail(user.getUsername(), recoverPasswordToken.getTokenId());

    emailService.sendEmail(email, "Recover Password", emailContent);
  }

  private User findUserByEmailOrThrowException(String email) {
    return userRepository.findByEmailAndDeletedIsFalse(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with email " + email +" does not exists."));
  }

  @Override
  public void recoverPassword(RecoverPasswordRequest request) {
    throwExceptionIfPasswordsAreNotEquals(request.password(), request.confirmPassword());
    throwExceptionIfTokenIsExpired(request.recoverPasswordToken());
    User user = tokenService.findUserByToken(request.recoverPasswordToken());
    user.setPassword(passwordEncoder.encode(request.password()));

    userRepository.save(user);
  }

  private void throwExceptionIfPasswordsAreNotEquals(String password, String confirmPassword) {
    if (!password.equals(confirmPassword))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
  }

  private void throwExceptionIfTokenIsExpired(UUID tokenId) {
    if (isTokenExpired(tokenId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Token has expired");
    }
  }

  @Override
  public void resendActivationEmail(SendEmailRequest sendEmailRequest) {
    User user = findUserByEmailOrThrowException(sendEmailRequest.email());
    ifUserIsActivatedThrowException(user);
    Token token = tokenService.findActiveTokenByUser(user);
    sendActivationEmail(user, token.getTokenId());
  }

  @Override
  public void sendNewActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest) {
    throwExceptionIfTokenIsNotExpired(sendNewActivationTokenRequest.activationToken());
    User user = tokenService.findUserByToken(sendNewActivationTokenRequest.activationToken());
    Token token = tokenService.saveToken(user);
    sendNewActivationEmail(user, token.getTokenId());
  }

  private void sendNewActivationEmail(User user, UUID activationToken) {
    String emailBody = emailBuilder.buildNewActivationEmail(user, activationToken);
    emailService.sendEmail(user.getEmail(), "New Activation Token", emailBody);
  }

  private void throwExceptionIfTokenIsNotExpired(UUID activationTokenId) {
    if (!isTokenExpired(activationTokenId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token is not expired");
    }
  }

}