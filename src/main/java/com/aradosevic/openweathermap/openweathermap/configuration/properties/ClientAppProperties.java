package com.aradosevic.openweathermap.openweathermap.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("custom.client-app")
public class ClientAppProperties {

  private String host;
  private String protocol;
}
