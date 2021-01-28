package com.aradosevic.openweathermap.openweathermap.dto.factory;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.dto.CityDto;
import java.util.stream.Collectors;

public class CityDtoFactory {

  public static CityDto from(City city) {
    CityDto dto = new CityDto();
    dto.setName(city.getName());
    dto.setDateTimeWeathers(city.getDateTimeWeathers().stream().map(DateTimeWeatherFactory::from)
        .collect(Collectors.toList()));
    return dto;
  }
}
