package com.aradosevic.openweathermap.openweathermap.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.exception.NotFoundException;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.Keys;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("cityServiceTest")
@SpringBootTest
public class CityServiceTest {

  @Autowired
  CityService service;

  @MockBean
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
