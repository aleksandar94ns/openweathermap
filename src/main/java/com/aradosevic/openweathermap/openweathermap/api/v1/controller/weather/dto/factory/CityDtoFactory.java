package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.factory;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.CityDto;
import java.util.stream.Collectors;

public class CityDtoFactory {

  public static CityDto getInstance(City city) {
    return CityDto.builder()
        .name(city.getName())
        .dateTimeWeathers(city.getDateTimeWeathers() == null ?
            null :
            city.getDateTimeWeathers().stream().map(DateTimeWeatherFactory::getInstance)
                .collect(Collectors.toList()))
        .build();
  }
}
