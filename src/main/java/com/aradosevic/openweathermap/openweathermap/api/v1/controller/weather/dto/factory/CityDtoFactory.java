package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.factory;

import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.CityDto;
import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.util.WeatherUtilities;
import java.util.List;

public class CityDtoFactory {

  public static CityDto getInstance(City city) {
    return CityDto.builder()
        .name(city.getName())
        .dateTimeWeathers(DateTimeWeatherFactory.getList(city.getDateTimeWeathers()))
        .build();
  }

  public static CityDto getInstanceWithName(City city) {
    return CityDto.builder()
        .name(city.getName())
        .build();
  }

  public static CityDto getCityWithAverage(String cityName, List<DateTimeWeather> dateTimeWeathers) {
    return CityDto.builder()
        .name(cityName)
        .averageTemp(WeatherUtilities.getAverageForData(dateTimeWeathers))
        .build();
  }

  public static CityDto getInstance(String cityName, List<DateTimeWeather> dateTimeWeathers) {
    return CityDto.builder()
        .name(cityName)
        .dateTimeWeathers(DateTimeWeatherFactory.getList(dateTimeWeathers))
        .averageTemp(WeatherUtilities.getAverageForData(dateTimeWeathers))
        .build();
  }
}
