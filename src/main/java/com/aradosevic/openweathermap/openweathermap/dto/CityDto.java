package com.aradosevic.openweathermap.openweathermap.dto;

import lombok.Data;

import java.util.List;

@Data
public class CityDto {

    String name;
    List<DateTimeWeatherDto> dateTimeWeathers;
}
