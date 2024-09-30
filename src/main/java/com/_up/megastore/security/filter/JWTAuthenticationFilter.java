package com._up.megastore.security.filter;

import com._up.megastore.exception.ExceptionPayload;
import com._up.megastore.security.services.JWTService;
import com._up.megastore.security.utils.Endpoints;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com._up.megastore.security.utils.Constants.BEARER;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(UserDetailsService userDetailsService, JWTService jwtService, ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String username;
        final String token = extractTokenFromRequest(request);

        if (token == null) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        try {
            username = jwtService.extractUsernameFromToken(token);
        } catch (JwtException e) {
            handleJWTExceptionResponse(response, e);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails))
                updateContext(request, userDetails);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean isWhiteListed = Arrays.stream(Endpoints.WHITE_LISTED_URLS)
                .anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));

        boolean isAllowedForGet = Arrays.stream(Endpoints.ALLOWED_TO_GET_BY_USERS_URLS)
                .anyMatch(url -> pathMatcher.match(url, request.getRequestURI()) && "GET".equals(request.getMethod()));

        return isWhiteListed || isAllowedForGet;
    }

    private void handleJWTExceptionResponse(HttpServletResponse response, JwtException e) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", frontendUrl);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        var exceptionPayload = buildExceptionPayload(e);
        response.getWriter().write(objectMapper.writeValueAsString(exceptionPayload));
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER)
                ? authorizationHeader.substring(BEARER.length())
                : null;
    }

    private void updateContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private static String getExceptionMessage(JwtException jwtException) {
        return switch (jwtException) {
            case ExpiredJwtException ignored -> "Session expired. Please log in again";
            case SignatureException ignored -> "Invalid token. Please contact an administrator";
            case ClaimJwtException ignored -> "JWT claims are invalid or malformed. Please contact an administrator";
            default -> jwtException.getMessage();
        };
    }

    private ExceptionPayload buildExceptionPayload(JwtException jwtException) {
        return new ExceptionPayload(
                getExceptionMessage(jwtException),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                jwtException.getStackTrace()
        );
    }

}