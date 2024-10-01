package com._up.megastore.config;

import com._up.megastore.data.enums.Role;
import com._up.megastore.security.entrypoint.GlobalAuthenticationEntryPoint;
import com._up.megastore.security.filter.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com._up.megastore.security.utils.Endpoints.ALLOWED_TO_ADMINISTRATORS_URLS;
import static com._up.megastore.security.utils.Endpoints.ALLOWED_TO_GET_BY_USERS_URLS;
import static com._up.megastore.security.utils.Endpoints.WHITE_LISTED_URLS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;
  private final JWTAuthenticationFilter jwtAuthenticationFilter;
  private final GlobalAuthenticationEntryPoint globalAuthenticationEntryPoint;

  public SecurityConfig(
          AuthenticationProvider authenticationProvider,
          JWTAuthenticationFilter jwtAuthenticationFilter,
          GlobalAuthenticationEntryPoint globalAuthenticationEntryPoint
  ) {
      this.authenticationProvider = authenticationProvider;
      this.jwtAuthenticationFilter = jwtAuthenticationFilter;
      this.globalAuthenticationEntryPoint = globalAuthenticationEntryPoint;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authRequest -> authRequest
            .requestMatchers(WHITE_LISTED_URLS).permitAll()
            .requestMatchers(HttpMethod.GET, ALLOWED_TO_GET_BY_USERS_URLS).permitAll()
            .requestMatchers(HttpMethod.OPTIONS).permitAll()
            .requestMatchers(ALLOWED_TO_ADMINISTRATORS_URLS).hasRole(Role.ADMIN.name()))
        .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer
                .authenticationEntryPoint(globalAuthenticationEntryPoint))
        .authenticationProvider(authenticationProvider)
        .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
