package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.controllers.responses.AuthResponse;
import com._up.megastore.data.model.User;
import com._up.megastore.security.services.JWTService;
import com._up.megastore.services.interfaces.IAuthService;
import com._up.megastore.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com._up.megastore.security.utils.Constants.BASIC;

@Service
public class AuthService implements IAuthService {

  private final IUserService userService;
  private final JWTService jwtService;

  public AuthService(IUserService userService, JWTService jwtService) {
    this.userService = userService;
      this.jwtService = jwtService;
  }

  @Override
  public void signUp(SignUpRequest signUpRequest) {
    userService.saveUser(signUpRequest);
  }

  @Override
  public AuthResponse signIn(String credentials) {
    String username = extractUsernameFromCredentials(credentials);

    User user = userService.findUserByUsernameOrThrowException(username);
    ifUserIsNotActivatedThrowException(user);
    String accessToken = jwtService.generateAccessToken(user);

    return new AuthResponse(user.getUserId(), accessToken, user.getRole());
  }

  private void ifUserIsNotActivatedThrowException(User user) {
    if (!user.isActivated()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username "+user.getUsername()+" is not activated");
    }
  }

  private String extractUsernameFromCredentials(String credentials) {
    credentials = removeBasicPrefix(credentials);
    String decodedString = decodeCredentials(credentials);
    return decodedString.split(":")[0];
  }

  private String removeBasicPrefix(String credentials) {
    if (credentials == null || !credentials.startsWith(BASIC)) {
      throw new IllegalArgumentException("Invalid credentials format");
    }
    return credentials.substring(BASIC.length());
  }

  private String decodeCredentials(String credentials) {
    byte[] decodedBytes = Base64.getDecoder().decode(credentials);
    return new String(decodedBytes, StandardCharsets.UTF_8);
  }

}