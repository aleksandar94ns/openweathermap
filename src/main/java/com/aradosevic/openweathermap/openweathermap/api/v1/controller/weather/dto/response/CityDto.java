package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDto {

  @JsonInclude(Include.NON_NULL)
  String name;

  @JsonInclude(Include.NON_EMPTY)
  List<DateTimeWeatherDto> dateTimeWeathers;

  @JsonInclude(Include.NON_NULL)
  Double averageTemp;
}
