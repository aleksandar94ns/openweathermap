package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DateTimeWeatherDto {

  private long cityId;
  private Long timestamp;
  private Double temperature;
  private Double minTemp;
  private Double maxTemp;

}
