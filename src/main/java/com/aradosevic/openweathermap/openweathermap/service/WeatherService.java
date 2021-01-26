package com.aradosevic.openweathermap.openweathermap.service;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.dto.CityDto;
import com.aradosevic.openweathermap.openweathermap.dto.factory.CityDtoFactory;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final CityRepository cityRepository;
    private final DateTimeWeatherRepository dateTimeWeatherRepository;

    @Autowired
    public WeatherService(CityRepository cityRepository, DateTimeWeatherRepository dateTimeWeatherRepository) {
        this.cityRepository = cityRepository;
        this.dateTimeWeatherRepository = dateTimeWeatherRepository;
    }

    public CityDto getCityAverage(String cityName) {
        City city = cityRepository.findByName(cityName);
        CityDto dto = CityDtoFactory.from(city);

        Double avgSum = 0.0;
        for (int i = 0; i < dto.getDateTimeWeathers().size(); i++) {
            avgSum+= dto.getDateTimeWeathers().get(i).getTemperature();
        }

        dto.setAverageTemp(avgSum/dto.getDateTimeWeathers().size());
        dto.setDateTimeWeathers(null);
        return dto;
    }
}
