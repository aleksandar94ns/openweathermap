package com.aradosevic.openweathermap.openweathermap.dto;

import lombok.Data;

@Data
public class DateTimeWeatherDto {

    private long cityId;
    private Long timestamp;
    private Double temperature;
    private Double minTemp;
    private Double maxTemp;

}
