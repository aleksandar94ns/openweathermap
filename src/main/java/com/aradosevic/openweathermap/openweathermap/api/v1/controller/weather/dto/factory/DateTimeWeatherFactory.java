package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.factory;

import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.DateTimeWeatherDto;
import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used to generate {@link DateTimeWeatherDto} from domain or primitive values
 */
public class DateTimeWeatherFactory {

  public static DateTimeWeatherDto getInstance(DateTimeWeather dateTimeWeather) {
    return DateTimeWeatherDto.builder()
        .temperature(dateTimeWeather.getTemperature())
        .maxTemp(dateTimeWeather.getMaxTemp())
        .minTemp(dateTimeWeather.getMinTemp())
        .time(new Date(dateTimeWeather.getTimestamp().getTime()))
        .build();
  }

  public static List<DateTimeWeatherDto> getList(List<DateTimeWeather> dateTimeWeathers) {
    //TODO: Quick check if list is empty, can be written better
    if (dateTimeWeathers == null) {
      return null;
    }

    return dateTimeWeathers.stream().map(DateTimeWeatherFactory::getInstance)
        .collect(Collectors.toList());
  }
}
