package com.aradosevic.openweathermap.openweathermap.service;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.Keys;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

  @InjectMocks
  CityService service;

  @Mock
  CityRepository repository;

  @Test
  void saveTest() {
    //given
    long id = 1;
    String name = "London";
    City city = City.builder().name(name).id(id).dateTimeWeathers(new ArrayList<>()).build();
    when(repository.save(any())).thenReturn(city);

    //when
    City savedCity = service.save(city);
    //then
    assertEquals(id, savedCity.getId());
    assertEquals(name, savedCity.getName());
    assertEquals(id, city.getId());
    assertEquals(name, city.getName());
  }

  @Test
  void findByName() {
    //given
    long id = 1;
    String name = "London";
    City city = City.builder().name(name).id(id).dateTimeWeathers(new ArrayList<>()).build();
    when(repository.findByName(any())).thenReturn(Optional.of(city));

    //when
    City savedCity = service.findByName(name);

    //then
    assertEquals(id, savedCity.getId());
    assertEquals(name, savedCity.getName());
    assertEquals(id, city.getId());
    assertEquals(name, city.getName());
  }

  @Test
  void throwExceptionOnFindByNameUsingWrongName() {
  //given
    long id = 1;
    String name = "London";
    City city = City.builder().name(name).id(id).dateTimeWeathers(new ArrayList<>()).build();
    when(repository.findByName(any())).thenReturn(Optional.empty());

    //when
    try
    {
      //Run exception throwing operation here
      City savedCity = service.findByName(name);
    }
    catch(RuntimeException re)
    {
      //then
      String message = Keys.CITY_BY_NAME_NOT_FOUND;
      assertEquals(message, re.getMessage());
    }
  }
}
