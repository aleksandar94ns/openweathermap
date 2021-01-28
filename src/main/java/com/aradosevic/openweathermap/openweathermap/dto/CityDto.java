package com.aradosevic.openweathermap.openweathermap.dto;

import java.util.List;
import lombok.Data;

@Data
public class CityDto {

    String name;
    List<DateTimeWeatherDto> dateTimeWeathers;
    Double averageTemp;
}
