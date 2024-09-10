package com._up.megastore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
  @Value("${app.cors.allowed-origins}")
  private String corsAllowedOrigins;

  @Bean
  public WebMvcConfigurer addCorsConfig() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        String[] originPatterns = corsAllowedOrigins.split(",");
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOriginPatterns(originPatterns)
            .allowCredentials(true);
      }
    };
  }
}