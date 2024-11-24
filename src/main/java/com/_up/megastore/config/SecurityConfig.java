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

import static com._up.megastore.security.utils.Endpoints.ADMIN_ORDER_MODIFICATON_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.ANY_USER_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.AUTH_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.DELETED_ENTITIES_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.DELETE_INFORMATION_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.ERROR_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.GET_ALL_ORDERS;
import static com._up.megastore.security.utils.Endpoints.GET_ONE_ORDER;
import static com._up.megastore.security.utils.Endpoints.PUBLIC_INFORMATION_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.REPORTS_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.SAVE_INFORMATION_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.UPDATE_INFORMATION_ENDPOINTS;
import static com._up.megastore.security.utils.Endpoints.USER_ORDER_MODIFICATION_ENDPOINTS;

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
            .requestMatchers(HttpMethod.OPTIONS).permitAll()
            .requestMatchers(AUTH_ENDPOINTS, ERROR_ENDPOINTS).permitAll()
            .requestMatchers(HttpMethod.POST, ANY_USER_ENDPOINTS).permitAll()
            .requestMatchers(HttpMethod.GET, DELETED_ENTITIES_ENDPOINTS).hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.GET, REPORTS_ENDPOINTS).hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.GET, PUBLIC_INFORMATION_ENDPOINTS).permitAll()
            .requestMatchers(HttpMethod.POST, SAVE_INFORMATION_ENDPOINTS).hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.PUT, UPDATE_INFORMATION_ENDPOINTS).hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.DELETE, DELETE_INFORMATION_ENDPOINTS).hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.POST, USER_ORDER_MODIFICATION_ENDPOINTS).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
            .requestMatchers(HttpMethod.POST, ADMIN_ORDER_MODIFICATON_ENDPOINTS).hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.GET, GET_ALL_ORDERS).hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.GET, GET_ONE_ORDER).hasAnyRole(Role.ADMIN.name(), Role.USER.name())
            .anyRequest().denyAll())
        .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer
                .authenticationEntryPoint(globalAuthenticationEntryPoint))
        .authenticationProvider(authenticationProvider)
        .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
