package com._up.megastore.security.entrypoint;

import com._up.megastore.exception.ExceptionPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", frontendUrl);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        var exceptionPayload = buildExceptionPayload(authException);

        response.getWriter().write(objectMapper.writeValueAsString(exceptionPayload));
    }

    private ExceptionPayload buildExceptionPayload(AuthenticationException authException) {
        return new ExceptionPayload(
                "Username or password are incorrect",
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                authException.getStackTrace()
        );
    }
}