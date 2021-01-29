package com.aradosevic.openweathermap.openweathermap.util;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import java.util.List;
import lombok.experimental.UtilityClass;

/**
 * Simple utility class that generates static methods.
 *
 * Currently only used for getting average temperature from the list of {@link DateTimeWeather} data.
 *
 * Can be expanded down the line with additional utilities.
 */
@UtilityClass
public class WeatherUtilities {

  public Double getAverageForData(List<DateTimeWeather> dateTimeWeathers) {
    //TODO: Quick check if list is empty, can be written better
    if (dateTimeWeathers == null || dateTimeWeathers.isEmpty()) {
      return null;
    }

    Double avgSum = 0.0;
    for (DateTimeWeather dateTimeWeather : dateTimeWeathers) {
      avgSum += dateTimeWeather.getTemperature();
    }

    return avgSum / dateTimeWeathers.size();
  }
}
