package com.aradosevic.openweathermap.openweathermap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.exception.NotFoundException;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.Keys;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

  @InjectMocks
  private CityService service;

  @Mock
  private CityRepository repository;

  /**
   * Test successful saving of the {@link City} into the database.
   */
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

  /**
   * Test fetching {@link City} by its {@link String} name from database.
   */
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

  /**
   * Test unsuccessful fetching from database of the {@link City} by its {@link String} name from
   * database.
   * <p>
   * It should throw {@link RuntimeException} that is a custom one {@link NotFoundException}.
   */
  @Test
  void throwExceptionOnFindByNameUsingWrongName() {
    //given
    long id = 1;
    String name = "London";
    when(repository.findByName(any())).thenReturn(Optional.empty());

    //when
    try {
      City savedCity = service.findByName(name);
    } catch (RuntimeException runtimeException) {
      //then
      String message = Keys.CITY_BY_NAME_NOT_FOUND;
      assertEquals(message, runtimeException.getMessage());
    }
  }

  //TODO: Implement tests for the rest of CityService methods, using the logic above
}
