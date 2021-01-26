package com.aradosevic.openweathermap.openweathermap.openweather;

import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.dto.openweathermap.OpenWeatherAppDto;
import com.aradosevic.openweathermap.openweathermap.dto.openweathermap.TimeDataDto;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Log4j2
public class OpenWeatherMapService {

    private final RestTemplate restTemplate;
    private final ClientAppProperties config;
    private final CityRepository cityRepository;
    private final DateTimeWeatherRepository dateTimeWeatherRepository;

    @Autowired
    public OpenWeatherMapService(RestTemplateBuilder restTemplateBuilder, ClientAppProperties config,
                                 CityRepository cityRepository, DateTimeWeatherRepository dateTimeWeatherRepository) {
        restTemplate = restTemplateBuilder.build();
        this.config = config;
        this.cityRepository = cityRepository;
        this.dateTimeWeatherRepository = dateTimeWeatherRepository;
    }

    public void fetchData() {
        saveCity(config.getCity1());
        saveCity(config.getCity2());
        saveCity(config.getCity3());
    }

    private void saveCity(String cityName) {
        City city = new City();
        city.setName(cityName);
        cityRepository.save(city);
        populateTemperatures(city);
    }

    private void populateTemperatures(City city) {
        OpenWeatherAppDto dto = restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={appId}&units=metric",
                OpenWeatherAppDto.class, city.getName(), config.getAppid());

        dto.getTimeData().forEach(timeDataDto -> saveTemperature(timeDataDto, city));
    }

    private void saveTemperature(TimeDataDto timeDataDto, City city) {
        DateTimeWeather dateTimeWeather = new DateTimeWeather();
        dateTimeWeather.setCity(city);
        dateTimeWeather.setTimestamp(new Date(timeDataDto.getTimestamp() * 1000));
        dateTimeWeather.setTemperature(timeDataDto.getMain().getTemperature());
        dateTimeWeather.setMinTemp(timeDataDto.getMain().getMinTemp());
        dateTimeWeather.setMaxTemp(timeDataDto.getMain().getMaxTemp());
        dateTimeWeatherRepository.save(dateTimeWeather);
    }
}
