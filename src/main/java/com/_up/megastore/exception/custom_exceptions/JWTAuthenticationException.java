package com._up.megastore.exception.custom_exceptions;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {

    public JWTAuthenticationException(String message, JwtException cause) {
        super(message, cause);
    }

}