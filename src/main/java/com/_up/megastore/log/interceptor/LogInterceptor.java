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
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        String requestBody = (HttpMethod.POST.name().equalsIgnoreCase(request.getMethod()))
                ? getRequestBody((ContentCachingRequestWrapper) request)
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

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] buf = request.getContentAsByteArray();
        return buf.length > 0 ? new String(buf, StandardCharsets.UTF_8) : null;
    }

}
