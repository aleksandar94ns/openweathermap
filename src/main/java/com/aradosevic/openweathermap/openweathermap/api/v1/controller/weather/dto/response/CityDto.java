package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDto {

  String name;
  List<DateTimeWeatherDto> dateTimeWeathers;
  Double averageTemp;
}
