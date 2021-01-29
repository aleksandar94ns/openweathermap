package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.service;

import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.factory.CityDtoFactory;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.CityDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.PageableCityDto;
import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.service.CityService;
import com.aradosevic.openweathermap.openweathermap.service.DateTimeWeatherService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

  public List<CityDto> getAllCities() {
    return cityService.getAll().stream().map(CityDtoFactory::getInstanceWithName)
        .collect(Collectors.toList());
  }

  public CityDto getCityTemperatures(String cityName) {
    return CityDtoFactory.getInstance(cityService.findByName(cityName));
  }

  public CityDto getCityAverageAfterDate(long date, String cityName) {
    //TODO: Refactor this, quick check if city exists
    cityService.findByName(cityName);

    return CityDtoFactory.getInstance(cityName, dateTimeWeatherService
        .findAllByCityNameAndAfterDate(cityName, new Date(date * SECONDS_TO_MILLISECONDS)));
  }

  public CityDto getCityAverageBeforeDate(long date, String cityName) {
    //TODO: Refactor this, quick check if city exists
    cityService.findByName(cityName);

    return CityDtoFactory.getInstance(cityName, dateTimeWeatherService
        .findAllByCityNameAndBeforeDate(cityName, new Date(date * SECONDS_TO_MILLISECONDS)));
  }

  public List<CityDto> getCitiesAverageBetweenDates(long start, long end) {
    List<CityDto> cities = new ArrayList<>();
    cities.add(getCityAverageFromDates(start, end, clientAppProperties.getCity1()));
    cities.add(getCityAverageFromDates(start, end, clientAppProperties.getCity2()));
    cities.add(getCityAverageFromDates(start, end, clientAppProperties.getCity3()));
    return cities;
  }

  public CityDto getCityAverageFromDates(long start, long end, String cityName) {
    //TODO: Refactor this, quick check if city exists
    cityService.findByName(cityName);

    return CityDtoFactory.getInstance(cityName, dateTimeWeatherService
        .findAllByCityNameAndBetweenDates(cityName, new Date(start * SECONDS_TO_MILLISECONDS),
            new Date(end * SECONDS_TO_MILLISECONDS)));
  }

  public List<CityDto> getAverageForAllCities() {
    List<CityDto> cities = new ArrayList<>();
    cities.add(getCityAverage(clientAppProperties.getCity1()));
    cities.add(getCityAverage(clientAppProperties.getCity2()));
    cities.add(getCityAverage(clientAppProperties.getCity3()));
    return cities;
  }

  public PageableCityDto getPageableAverageForAllCities(String filter, Pageable pageable) {
    List<CityDto> cities = new ArrayList<>();
    cities.add(getPageableCityAverage(clientAppProperties.getCity1(), filter, pageable));
    cities.add(getPageableCityAverage(clientAppProperties.getCity2(), filter, pageable));
    cities.add(getPageableCityAverage(clientAppProperties.getCity3(), filter, pageable));
    return map(cities);
  }

  private PageableCityDto map(List<CityDto> cityDtos) {
    PageableCityDto pageableSearchResultDto = new PageableCityDto();
    pageableSearchResultDto.setTotalHitsCount(cityDtos.size());
    pageableSearchResultDto.setCurrentPageCount(cityDtos.size());

    pageableSearchResultDto.setCityDtos(cityDtos);
    return pageableSearchResultDto;
  }

  private CityDto getPageableCityAverage(String cityName, String filter, Pageable pageable) {
    return CityDtoFactory
        .getCityWithAverage(cityName, dateTimeWeatherService.findByCityName(cityName, pageable));
  }

  public CityDto getCityAverage(String cityName) {
    //TODO: Refactor this, quick check if city exists
    cityService.findByName(cityName);

    return CityDtoFactory
        .getCityWithAverage(cityName, dateTimeWeatherService.findByCityName(cityName));
  }

  public List<CityDto> getAverageForCitiesBetweenDates(List<String> cities, long start, long end) {
    List<CityDto> cityDtoList = new ArrayList<>();
    for (String city : cities) {
      cityDtoList.add(getCityAverageFromDates(start, end, city));
    }
    return cityDtoList;
  }
}
