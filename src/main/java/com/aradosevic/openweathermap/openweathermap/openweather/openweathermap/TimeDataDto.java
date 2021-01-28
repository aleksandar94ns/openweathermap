package com.aradosevic.openweathermap.openweathermap.openweather.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TimeDataDto {

  @JsonProperty("dt")
  private Long timestamp;

  private MainTimeDataDto main;
}
