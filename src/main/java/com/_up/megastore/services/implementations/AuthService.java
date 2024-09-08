package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.AuthRequest;
import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.controllers.responses.AuthResponse;
import com._up.megastore.data.model.User;
import com._up.megastore.security.services.JWTService;
import com._up.megastore.services.interfaces.IAuthService;
import com._up.megastore.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
  public AuthResponse login(AuthRequest authRequest) {
    authenticateUser(authRequest.username(), authRequest.password());

    User user = userService.findUserByUsernameOrThrowException(authRequest.username());
//    ifUserIsNotEnabledThrowException(user);
    String accessToken = generateAccessToken(user);

    return new AuthResponse(user.getUserId(), accessToken);
  }

  private void authenticateUser(String username, String password) {
    UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(username, password);
    authenticationManager.authenticate(authToken);
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }

  private String generateAccessToken(User user) {
    Date expirationDate = new Date(System.currentTimeMillis() + accessTokenExpiration);
    return jwtService.generateToken(user, expirationDate);
  }

//  private void ifUserIsNotEnabledThrowException(User user) {
//    if (!user.isEnabled()) {
//      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username "+user.getUsername()+" is not enabled");
//    }
//  }

}