package com.aradosevic.openweathermap.openweathermap.service;

import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.dto.CityDto;
import com.aradosevic.openweathermap.openweathermap.dto.DateTimeWeatherDto;
import com.aradosevic.openweathermap.openweathermap.dto.factory.CityDtoFactory;
import com.aradosevic.openweathermap.openweathermap.dto.factory.DateTimeWeatherFactory;
import com.aradosevic.openweathermap.openweathermap.exception.NotFoundException;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.Keys;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  private final CityRepository cityRepository;
  private final DateTimeWeatherRepository dateTimeWeatherRepository;
  private final ClientAppProperties clientAppProperties;

  @Autowired
  public WeatherService(CityRepository cityRepository,
      DateTimeWeatherRepository dateTimeWeatherRepository,
      ClientAppProperties clientAppProperties) {
    this.cityRepository = cityRepository;
    this.dateTimeWeatherRepository = dateTimeWeatherRepository;
    this.clientAppProperties = clientAppProperties;
  }

  public CityDto getCityAverageAfterDate(long date, String cityName) {
    City city = cityRepository.findByName(cityName)
        .orElseThrow(() -> new NotFoundException(Keys.CITY_BY_NAME_NOT_FOUND, cityName));
    CityDto dto = CityDtoFactory.getInstance(city);
    Date afterDate = new Date(date * 1000);

    List<DateTimeWeatherDto> dateDto = DateTimeWeatherFactory.getList(
        dateTimeWeatherRepository.findByCityNameAndTimestampAfter(cityName, afterDate));
    dto.setDateTimeWeathers(dateDto);
    dto.setAverageTemp(getAverage(dateDto));

    return dto;
  }

  public CityDto getCityAverageBeforeDate(long date, String cityName) {
    City city = cityRepository.findByName(cityName)
        .orElseThrow(() -> new NotFoundException(Keys.CITY_BY_NAME_NOT_FOUND, cityName));
    CityDto dto = CityDtoFactory.getInstance(city);
    Date beforeDate = new Date(date * 1000);

    List<DateTimeWeatherDto> dateDto = DateTimeWeatherFactory.getList(
        dateTimeWeatherRepository.findByCityNameAndTimestampBefore(cityName, beforeDate));
    dto.setDateTimeWeathers(dateDto);
    dto.setAverageTemp(getAverage(dateDto));

    return dto;
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
        dateTimeWeatherRepository.findByCityNameAndTimestampBetween(cityName, startDate, endDate));
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
    City city = cityRepository.findByName(cityName)
        .orElseThrow(() -> new NotFoundException(Keys.CITY_BY_NAME_NOT_FOUND, cityName));
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
