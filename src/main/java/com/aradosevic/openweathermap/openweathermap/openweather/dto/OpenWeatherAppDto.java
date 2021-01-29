package com.aradosevic.openweathermap.openweathermap.openweather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class OpenWeatherAppDto {

  String cod;
  String message;
  Integer cnt;

  @JsonProperty("list")
  List<TimeDataDto> timeData;
}
