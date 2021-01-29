package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableCityDto {
  private long totalHitsCount;
  private long currentPageCount;
  List<CityDto> cityDtos;
}
