package com.aradosevic.openweathermap.openweathermap.openweather;

import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.exception.NotFoundException;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.DefaultMessages;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.Keys;
import com.aradosevic.openweathermap.openweathermap.openweather.dto.OpenWeatherAppDto;
import com.aradosevic.openweathermap.openweathermap.openweather.dto.TimeDataDto;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import java.util.Date;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Command line runner class that is being called on application startup to fetch and populate data
 * from https://openweathermap.org/ site with given API key.
 * <p>
 * Api key can be set in application.properties
 */
@Component
@Log4j2
public class OpenWeatherMapCommandLine implements CommandLineRunner {

  private final RestTemplate restTemplate;
  private final ClientAppProperties config;
  private final CityRepository cityRepository;
  private final DateTimeWeatherRepository dateTimeWeatherRepository;

  @Autowired
  public OpenWeatherMapCommandLine(RestTemplateBuilder restTemplateBuilder,
      ClientAppProperties config,
      CityRepository cityRepository, DateTimeWeatherRepository dateTimeWeatherRepository) {
    restTemplate = restTemplateBuilder.build();
    this.config = config;
    this.cityRepository = cityRepository;
    this.dateTimeWeatherRepository = dateTimeWeatherRepository;
  }

  @Override
  public void run(String... args) {
    populateData();
  }

  /**
   * Fetches data from all of the cities that are defined in application.properties.
   */
  public void populateData() {
    saveCity(config.getCity1());
    saveCity(config.getCity2());
    saveCity(config.getCity3());
  }

  /**
   * Save each of the defined cities in database.
   *
   * @param cityName - Name of the city that is being saved
   */
  private void saveCity(String cityName) {
    City city = City.builder().name(cityName).build();
    cityRepository.save(city);
    fetchTemperatures(city);
  }

  /**
   * Get specific city temperature with provided API, that will
   *
   * @param city - {@link City} that that needs temperature fetching
   */
  private void fetchTemperatures(City city) {
    OpenWeatherAppDto dto = restTemplate.getForObject(
        "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={appId}&units=metric",
        OpenWeatherAppDto.class, city.getName(), config.getAppId());

    //TODO: Refactor this quick null checker for bad API request.
    if (dto == null) {
      throw new NotFoundException(Keys.RESOURCE_NOT_FOUND, DefaultMessages.RESOURCE_NOT_FOUND);
    }

    dto.getTimeData().forEach(timeDataDto -> saveTemperature(timeDataDto, city));
  }

  /**
   * Convert {@link TimeDataDto} values into {@link DateTimeWeather} and save them into repository
   * for provided {@link City}.
   *
   * @param timeDataDto {@link TimeDataDto} values from API that need to be converted into {@link
   *                    DateTimeWeather}
   * @param city        {@link City} that will be attached as parent for newly created {@link
   *                    DateTimeWeather}
   */
  private void saveTemperature(TimeDataDto timeDataDto, City city) {
    final Integer secondsToMilliseconds = 1000;

    dateTimeWeatherRepository.save(
        DateTimeWeather.builder()
            .city(city)
            .timestamp(new Date(timeDataDto.getTimestamp() * secondsToMilliseconds))
            .temperature(timeDataDto.getMain().getTemperature())
            .maxTemp(timeDataDto.getMain().getMinTemp())
            .minTemp(timeDataDto.getMain().getMaxTemp())
            .build());
  }
}
