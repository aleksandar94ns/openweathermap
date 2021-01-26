package com.aradosevic.openweathermap.openweathermap.dto.factory;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.dto.CityDto;

public class CityDtoFactory {
    public static CityDto from(City city) {
        CityDto dto = new CityDto();
        dto.setName(city.getName());
        return dto;
    }
}
