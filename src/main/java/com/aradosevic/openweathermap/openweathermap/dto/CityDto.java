package com.aradosevic.openweathermap.openweathermap.dto;

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
