package com.aradosevic.openweathermap.openweathermap.openweather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MainTimeDataDto {

  @JsonProperty("temp")
  Double temperature;

  @JsonProperty("temp_min")
  Double minTemp;

  @JsonProperty("temp_max")
  Double maxTemp;
}
