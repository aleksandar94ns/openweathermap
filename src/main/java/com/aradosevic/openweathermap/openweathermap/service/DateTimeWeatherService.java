package com.aradosevic.openweathermap.openweathermap.service;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service used for communicating with {@link DateTimeWeatherRepository}.
 *
 * It should always receive and respond with domain models.
 */
@Service
public class DateTimeWeatherService {

  private final DateTimeWeatherRepository repository;

  @Autowired
  public DateTimeWeatherService(DateTimeWeatherRepository repository) {
    this.repository = repository;
  }

  public DateTimeWeather save(DateTimeWeather dateTimeWeather) {
    return repository.save(dateTimeWeather);
  }

  public List<DateTimeWeather> findByCityName(String cityName) {
    return repository.findByCityName(cityName);
  }

  public List<DateTimeWeather> findAllByCityNameAndAfterDate(String cityName, Date afterDate) {
    return repository.findByCityNameAndTimestampAfter(cityName, afterDate);
  }

  public List<DateTimeWeather> findAllByCityNameAndBeforeDate(String cityName, Date afterDate) {
    return repository.findByCityNameAndTimestampBefore(cityName, afterDate);
  }

  public List<DateTimeWeather> findAllByCityNameAndBetweenDates(String cityName, Date startingDate,
      Date endingDate) {
    return repository.findByCityNameAndTimestampBetween(cityName, startingDate, endingDate);
  }
}
