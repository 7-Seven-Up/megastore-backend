package com._up.megastore.security.entrypoint;

import com._up.megastore.exception.ExceptionPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static com._up.megastore.security.utils.Constants.BASIC_AUTH_EXCEPTION_MESSAGE;
import static com._up.megastore.security.utils.Constants.CLAIMS_JWT_EXCEPTION_MESSAGE;
import static com._up.megastore.security.utils.Constants.EXPIRED_JWT_EXCEPTION_MESSAGE;
import static com._up.megastore.security.utils.Constants.SIGNATURE_EXCEPTION_MESSAGE;

@Component
@RequiredArgsConstructor
public class GlobalAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        configureResponse(response);

        final var exceptionPayload = buildExceptionPayload(
                (authException.getCause() != null)
                    ? authException.getCause()
                    : authException
        );

        response.getWriter().write(objectMapper.writeValueAsString(exceptionPayload));
    }

    private void configureResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", frontendUrl);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private ExceptionPayload buildExceptionPayload(Throwable throwable) {
        return new ExceptionPayload(
                getExceptionMessage(throwable),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                throwable.getStackTrace()
        );
    }

    private static String getExceptionMessage(Throwable throwable) {
        return switch (throwable) {
            case ExpiredJwtException ignored -> EXPIRED_JWT_EXCEPTION_MESSAGE;
            case SignatureException ignored -> SIGNATURE_EXCEPTION_MESSAGE;
            case ClaimJwtException ignored -> CLAIMS_JWT_EXCEPTION_MESSAGE;
            case BadCredentialsException ignored -> BASIC_AUTH_EXCEPTION_MESSAGE;
            case InternalAuthenticationServiceException ignored -> BASIC_AUTH_EXCEPTION_MESSAGE;
            default -> throwable.getMessage();
        };
    }

}