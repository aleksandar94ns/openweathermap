package com.aradosevic.openweathermap.openweathermap.util;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WeatherUtilities {

  public Double getAverageForData(List<DateTimeWeather> dateTimeWeathers) {
    //TODO: Quick check if list is empty, can be written better
    if (dateTimeWeathers == null) {
      return null;
    }

    Double avgSum = 0.0;
    for (DateTimeWeather dateTimeWeather : dateTimeWeathers) {
      avgSum += dateTimeWeather.getTemperature();
    }

    return avgSum / dateTimeWeathers.size();
  }
}
