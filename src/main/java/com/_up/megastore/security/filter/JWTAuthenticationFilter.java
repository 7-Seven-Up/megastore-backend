package com._up.megastore.security.filter;

import com._up.megastore.exception.custom_exceptions.JWTAuthenticationException;
import com._up.megastore.security.services.JWTService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Stream;

import static com._up.megastore.security.utils.Constants.BEARER;
import static com._up.megastore.security.utils.Constants.JWT_EXCEPTION_DEFAULT_MESSAGE;
import static com._up.megastore.security.utils.Endpoints.ANY_USER_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.AUTH_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.ERROR_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.PUBLIC_INFORMATION_ENDPOINTS;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final PathMatcher pathMatcher;

    public JWTAuthenticationFilter(UserDetailsService userDetailsService, JWTService jwtService, PathMatcher pathMatcher) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.pathMatcher = pathMatcher;
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
        boolean isOptionsMethod = request.getMethod().equals(HttpMethod.OPTIONS.name());
        boolean isAuthEndpoint = pathMatcher.match(AUTH_ENDPOINTS, request.getRequestURI());
        boolean isErrorEndpoint = pathMatcher.match(ERROR_ENDPOINTS, request.getRequestURI());

        boolean isAnyUserEndpoint = pathMatcher.match(ANY_USER_ENDPOINTS, request.getRequestURI())
                && request.getMethod().equals(HttpMethod.POST.name());

        boolean isPublicInformationEndpoint = Stream.of(PUBLIC_INFORMATION_ENDPOINTS)
                .anyMatch(url -> pathMatcher.match(url, request.getRequestURI()))
                && request.getMethod().equals(HttpMethod.GET.name());

        return isOptionsMethod || isAuthEndpoint || isErrorEndpoint || isAnyUserEndpoint || isPublicInformationEndpoint;
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