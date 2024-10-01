package com._up.megastore.security.filter;

import com._up.megastore.exception.custom_exceptions.JWTAuthenticationException;
import com._up.megastore.security.services.JWTService;
import com._up.megastore.security.utils.Endpoints;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
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
import java.util.Arrays;

import static com._up.megastore.security.utils.Constants.BEARER;
import static com._up.megastore.security.utils.Constants.JWT_EXCEPTION_DEFAULT_MESSAGE;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;

    public JWTAuthenticationFilter(UserDetailsService userDetailsService, JWTService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

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
            throw new JWTAuthenticationException(JWT_EXCEPTION_DEFAULT_MESSAGE, e);
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

}