package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.service;

import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.CityDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.DateTimeWeatherDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.factory.CityDtoFactory;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.factory.DateTimeWeatherFactory;
import com.aradosevic.openweathermap.openweathermap.service.CityService;
import com.aradosevic.openweathermap.openweathermap.service.DateTimeWeatherService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  private final Integer SECONDS_TO_MILLISECONDS = 1000;

  private final CityService cityService;
  private final DateTimeWeatherService dateTimeWeatherService;
  private final ClientAppProperties clientAppProperties;

  @Autowired
  public WeatherService(CityService cityService,
      DateTimeWeatherService dateTimeWeatherService,
      ClientAppProperties clientAppProperties) {
    this.cityService = cityService;
    this.dateTimeWeatherService = dateTimeWeatherService;
    this.clientAppProperties = clientAppProperties;
  }

  public CityDto getCityAverageAfterDate(long date, String cityName) {
    //TODO: Refactor this, quick check if city exists
    cityService.findByName(cityName);

    List<DateTimeWeatherDto> dateDto = DateTimeWeatherFactory.getList(
        dateTimeWeatherService.findAllByCityNameAndAfterDate(cityName, new Date(date * SECONDS_TO_MILLISECONDS)));

    return CityDto.builder()
        .name(cityName)
        .dateTimeWeathers(dateDto)
        .averageTemp(getAverage(dateDto))
        .build();
  }

  public CityDto getCityAverageBeforeDate(long date, String cityName) {
    //TODO: Refactor this, quick check if city exists
    cityService.findByName(cityName);

    List<DateTimeWeatherDto> dateDto = DateTimeWeatherFactory.getList(
        dateTimeWeatherService
            .findAllByCityNameAndBeforeDate(cityName, new Date(date * SECONDS_TO_MILLISECONDS)));

    return CityDto.builder()
        .name(cityName)
        .dateTimeWeathers(dateDto)
        .averageTemp(getAverage(dateDto))
        .build();
  }

  public List<CityDto> getCitiesAverageBetweenDates(long start, long end) {
    List<CityDto> cities = new ArrayList<>();
    cities.add(getCityAverageFromDates(start, end, clientAppProperties.getCity1()));
    cities.add(getCityAverageFromDates(start, end, clientAppProperties.getCity2()));
    cities.add(getCityAverageFromDates(start, end, clientAppProperties.getCity3()));
    return cities;
  }

  public CityDto getCityAverageFromDates(long start, long end, String cityName) {
    CityDto dto = CityDto.builder().build();

    Date startDate = new Date(start * 1000);
    Date endDate = new Date(end * 1000);

    List<DateTimeWeatherDto> dateDto = DateTimeWeatherFactory.getList(
        dateTimeWeatherService.findAllByCityNameAndBetweenDates(cityName, startDate, endDate));
    dto.setDateTimeWeathers(dateDto);
    dto.setAverageTemp(getAverage(dateDto));
    dto.setName(cityName);

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
    City city = cityService.findByName(cityName);
    CityDto dto = CityDtoFactory.getInstance(city);

    dto.setAverageTemp(getAverage(dto.getDateTimeWeathers()));
    dto.setDateTimeWeathers(null);
    return dto;
  }

  public double getAverage(List<DateTimeWeatherDto> dateTimeWeatherDtos) {
    Double avgSum = 0.0;
    for (DateTimeWeatherDto dateTimeWeatherDto : dateTimeWeatherDtos) {
      avgSum += dateTimeWeatherDto.getTemperature();
    }

    return avgSum / dateTimeWeatherDtos.size();
  }
}
