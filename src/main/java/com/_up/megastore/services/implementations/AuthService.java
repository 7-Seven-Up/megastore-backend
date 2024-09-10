package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.AuthRequest;
import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.controllers.responses.AuthResponse;
import com._up.megastore.data.model.User;
import com._up.megastore.security.services.JWTService;
import com._up.megastore.services.interfaces.IAuthService;
import com._up.megastore.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class AuthService implements IAuthService {

  private final IUserService userService;
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;

  public AuthService(IUserService userService, AuthenticationManager authenticationManager, JWTService jwtService) {
    this.userService = userService;
      this.authenticationManager = authenticationManager;
      this.jwtService = jwtService;
  }

  @Value("${jwt.access-token-expiration}")
  private Long accessTokenExpiration;

  @Override
  public void signUp(SignUpRequest signUpRequest) {
    userService.saveUser(signUpRequest);
  }

  @Override
  public AuthResponse signIn(AuthRequest authRequest) {
    authenticateUser(authRequest.username(), authRequest.password());

    User user = userService.findUserByUsernameOrThrowException(authRequest.username());
    ifUserIsNotActivatedThrowException(user);
    String accessToken = generateAccessToken(user);

    return new AuthResponse(user.getUserId(), accessToken, user.getRole());
  }

  private void authenticateUser(String username, String password) {
    authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(username, password) );
  }

  private String generateAccessToken(User user) {
    Date expirationDate = new Date(System.currentTimeMillis() + accessTokenExpiration);
    return jwtService.generateToken(user, expirationDate);
  }

  private void ifUserIsNotActivatedThrowException(User user) {
    if (!user.isActivated()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username "+user.getUsername()+" is not activated");
    }
  }

}