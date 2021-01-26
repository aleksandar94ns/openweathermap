package com.aradosevic.openweathermap.openweathermap.dto.factory;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.dto.DateTimeWeatherDto;

import java.util.List;
import java.util.stream.Collectors;

public class DateTimeWeatherFactory {
    public static DateTimeWeatherDto from(DateTimeWeather dateTimeWeather) {
        DateTimeWeatherDto dto = new DateTimeWeatherDto();
        dto.setCityId(dateTimeWeather.getCity().getId());
        dto.setTemperature(dateTimeWeather.getTemperature());
        dto.setTimestamp(dateTimeWeather.getTimestamp().getTime());
        dto.setMaxTemp(dateTimeWeather.getMaxTemp());
        dto.setMinTemp(dateTimeWeather.getMinTemp());
        return dto;
    }

    public static List<DateTimeWeatherDto> fromList(List<DateTimeWeather> dateTimeWeathers) {
        return dateTimeWeathers.stream().map(DateTimeWeatherFactory::from).collect(Collectors.toList());
    }
}
