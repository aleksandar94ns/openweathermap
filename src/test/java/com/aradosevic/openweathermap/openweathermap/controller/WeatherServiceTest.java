package com.aradosevic.openweathermap.openweathermap.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.CityDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.service.WeatherService;
import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.service.CityService;
import com.aradosevic.openweathermap.openweathermap.service.DateTimeWeatherService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

  @InjectMocks
  private WeatherService weatherService;

  @Mock
  private CityService cityService;

  @Mock
  private ClientAppProperties clientAppProperties;

  @Mock
  private DateTimeWeatherService dateTimeWeatherService;

  /**
   * Test creating {@link CityDto} with {@link DateTimeWeather} 
   */
  @Test
  void getCityTemperatures() {
    //given
    long id = 1;
    String cityName = "London";
    City city = City.builder().name(cityName).id(id).build();

    List<DateTimeWeather> dateTimeWeathers = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      double temperature = i;
      double maxTemp = i + 3.0;
      double minTemp = i - 3.0;

      dateTimeWeathers.add(DateTimeWeather.builder()
          .id(i)
          .city(city)
          .timestamp(new Date())
          .temperature(temperature)
          .minTemp(minTemp)
          .maxTemp(maxTemp)
          .build());
    }

    city = City.builder().name(cityName).id(id).dateTimeWeathers(dateTimeWeathers).build();

    when(cityService.findByName(anyString())).thenReturn(city);

    //when
    CityDto cityDto = weatherService.getCityTemperatures(cityName);

    //then
    assertEquals(cityName, cityDto.getName());
    assertEquals(cityName, city.getName());
    assertEquals(city.getDateTimeWeathers().size(), cityDto.getDateTimeWeathers().size());
  }

  @Test
  void getAverageForAllCities() {
    //given
    String cityName1 = "London";
    when(clientAppProperties.getCity1()).thenReturn(cityName1);
    String cityName2 = "Tokyo";
    when(clientAppProperties.getCity2()).thenReturn(cityName2);
    String cityName3 = "Belgrade";
    when(clientAppProperties.getCity3()).thenReturn(cityName3);

    long id = 1;
    String cityName = "London";
    City city = City.builder().name(cityName).id(id).build();

    List<DateTimeWeather> dateTimeWeathers = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      double temperature = i;
      double maxTemp = i + 3.0;
      double minTemp = i - 3.0;

      dateTimeWeathers.add(DateTimeWeather.builder()
          .id(i)
          .city(city)
          .timestamp(new Date())
          .temperature(temperature)
          .minTemp(minTemp)
          .maxTemp(maxTemp)
          .build());
    }

    city = City.builder().name(cityName).id(id).dateTimeWeathers(dateTimeWeathers).build();

    when(cityService.findByName(anyString())).thenReturn(city);
    when(dateTimeWeatherService.findByCityName(anyString())).thenReturn(dateTimeWeathers);

    //when
    List<CityDto> cityDtoList = weatherService.getAverageForAllCities();

    //then
    assertEquals(3, cityDtoList.size());

    assertEquals(cityName1, cityDtoList.get(0).getName());
    assertEquals(cityName2, cityDtoList.get(1).getName());
    assertEquals(cityName3, cityDtoList.get(2).getName());

    assertNull(cityDtoList.get(0).getDateTimeWeathers());
    assertNull(cityDtoList.get(1).getDateTimeWeathers());
    assertNull(cityDtoList.get(2).getDateTimeWeathers());

    assertNotNull(cityDtoList.get(0).getAverageTemp());
    assertNotNull(cityDtoList.get(1).getAverageTemp());
    assertNotNull(cityDtoList.get(2).getAverageTemp());
  }

  //TODO: Implement tests for the rest of WeatherService methods, using the logic above
}
