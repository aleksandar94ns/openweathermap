package com.aradosevic.openweathermap.openweathermap.service;

import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.dto.CityDto;
import com.aradosevic.openweathermap.openweathermap.dto.factory.CityDtoFactory;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WeatherService {

    private final CityRepository cityRepository;
    private final DateTimeWeatherRepository dateTimeWeatherRepository;
    private final ClientAppProperties clientAppProperties;

    @Autowired
    public WeatherService(CityRepository cityRepository, DateTimeWeatherRepository dateTimeWeatherRepository,
                          ClientAppProperties clientAppProperties) {
        this.cityRepository = cityRepository;
        this.dateTimeWeatherRepository = dateTimeWeatherRepository;
        this.clientAppProperties = clientAppProperties;
    }

    //TODO: Implement average from dates
    public CityDto getCityAverageFromDates(long start, long end, String cityName) {
        City city = cityRepository.findByName(cityName);
        CityDto dto = CityDtoFactory.from(city);

        Date startDate = new Date(start);
        Date endDate = new Date(end);

        return dto;
    }

    public List<CityDto> getAverageForAllCities() {
        List<CityDto> cities = new ArrayList<>();
        cities.add(getCityAverage(clientAppProperties.getCity1()));
        cities.add(getCityAverage(clientAppProperties.getCity2()));
        cities.add(getCityAverage(clientAppProperties.getCity3()));
        return cities;
    }

    public CityDto getCityAverage(String cityName) {
        City city = cityRepository.findByName(cityName);
        CityDto dto = CityDtoFactory.from(city);

        Double avgSum = 0.0;
        for (int i = 0; i < dto.getDateTimeWeathers().size(); i++) {
            avgSum += dto.getDateTimeWeathers().get(i).getTemperature();
        }

        dto.setAverageTemp(avgSum / dto.getDateTimeWeathers().size());
        dto.setDateTimeWeathers(null);
        return dto;
    }
}
