package com.aradosevic.openweathermap.openweathermap.dto.factory;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.dto.DateTimeWeatherDto;
import java.util.List;
import java.util.stream.Collectors;

public class DateTimeWeatherFactory {

  public static DateTimeWeatherDto getInstance(DateTimeWeather dateTimeWeather) {
    return DateTimeWeatherDto.builder()
        .cityId(dateTimeWeather.getCity().getId())
        .temperature(dateTimeWeather.getTemperature())
        .maxTemp(dateTimeWeather.getMaxTemp())
        .minTemp(dateTimeWeather.getMinTemp())
        .timestamp(dateTimeWeather.getTimestamp().getTime())
        .build();
  }

  public static List<DateTimeWeatherDto> getList(List<DateTimeWeather> dateTimeWeathers) {
    return dateTimeWeathers.stream().map(DateTimeWeatherFactory::getInstance)
        .collect(Collectors.toList());
  }
}
