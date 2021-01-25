package com.aradosevic.openweathermap.openweathermap.configuration;

import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class CorsConfiguration {

  @Bean
  public WebMvcConfigurer corsConfigurer(ClientAppProperties config) {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                UriComponentsBuilder.newInstance()
                    .scheme(config.getProtocol())
                    .host(config.getHost())
                    .build()
                    .toString())
            .allowedMethods("*");
      }
    };
  }
}
