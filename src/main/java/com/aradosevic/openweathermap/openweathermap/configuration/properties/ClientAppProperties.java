package com.aradosevic.openweathermap.openweathermap.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Component used to map custom configuration properties, such as city names for forecast and api id.
 */
@Component
@Data
@ConfigurationProperties("custom.client-app")
public class ClientAppProperties {

  private String city1;
  private String city2;
  private String city3;

  private String appId;
}
