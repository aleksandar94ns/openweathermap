package com.aradosevic.openweathermap.openweathermap.service;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.exception.NotFoundException;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.Keys;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

  private final CityRepository repository;

  @Autowired
  public CityService(CityRepository repository) {
    this.repository = repository;
  }

  public City save(City city) {
    return repository.save(city);
  }

  public City findByName(String name) {
    return repository.findByName(name)
        .orElseThrow(() -> new NotFoundException(Keys.CITY_BY_NAME_NOT_FOUND, name));
  }

  public List<City> getAll() {
    return repository.findAll();
  }
}
