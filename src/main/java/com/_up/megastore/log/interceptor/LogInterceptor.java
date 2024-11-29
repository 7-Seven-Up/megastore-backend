package com._up.megastore.log.interceptor;

import com._up.megastore.log.model.ILogRepository;
import com._up.megastore.log.model.Log;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class LogInterceptor implements HandlerInterceptor {

    private final ILogRepository logRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws IOException {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        String requestBody = (HttpMethod.POST.name().equalsIgnoreCase(request.getMethod()))
                ? request.getReader().lines().collect(Collectors.joining(System.lineSeparator()))
                : "No body";

        logRepository.save(
                new Log(
                        UUID.randomUUID(),
                        username,
                        requestBody,
                        request.getMethod(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                )
        );

        return true;
    }
}
