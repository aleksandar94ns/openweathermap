package com.aradosevic.openweathermap.openweathermap.service;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

  public Page<DateTimeWeather> findByCityName(String cityName, Pageable pageable) {
    return repository.findByCityName(cityName, pageable);
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
