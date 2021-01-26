package com.aradosevic.openweathermap.openweathermap.dto.factory;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.dto.DateTimeWeatherDto;

public class DateTimeWeatherFactory {
    public static DateTimeWeatherDto from(DateTimeWeather dateTimeWeather) {
        DateTimeWeatherDto dto = new DateTimeWeatherDto();
        dto.setCityId(dateTimeWeather.getCity().getId());
        dto.setTemperature(dateTimeWeather.getTemperature());
        dto.setTimestamp(dateTimeWeather.getTimestamp());
        dto.setMaxTemp(dateTimeWeather.getMaxTemp());
        dto.setMinTemp(dateTimeWeather.getMinTemp());
        return dto;
    }
}
