package com.aradosevic.openweathermap.openweathermap.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import com.google.common.collect.Iterables;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DateTimeWeatherServiceTest {

  @InjectMocks
  DateTimeWeatherService service;

  @Mock
  DateTimeWeatherRepository repository;

  @Test
  void saveTest() {
    //given
    long id = 1;
    Date timestamp = new Date();
    Double temperature = 7.0;
    Double minTemp = 0.0;
    Double maxTemp = 10.0;
    String cityName = "London";
    City city = City.builder().id(id).name(cityName).build();
    DateTimeWeather dateTimeWeather = DateTimeWeather.builder()
        .id(id)
        .city(city)
        .timestamp(timestamp)
        .temperature(temperature)
        .minTemp(minTemp)
        .maxTemp(maxTemp)
        .build();
    when(repository.save(any())).thenReturn(dateTimeWeather);

    //when
    DateTimeWeather savedDateTimeWeather = service.save(dateTimeWeather);

    //then
    Assertions.assertEquals(id, dateTimeWeather.getId());
    Assertions.assertEquals(id, dateTimeWeather.getCity().getId());
    Assertions.assertEquals(temperature, dateTimeWeather.getTemperature());
  }

  @Test
  void findByCityName() {
    //given
    long id = 1;
    Date timestamp = new Date();
    Double temperature = 7.0;
    Double minTemp = 0.0;
    Double maxTemp = 10.0;
    String cityName = "London";
    City city = City.builder().id(id).name(cityName).build();
    DateTimeWeather dateTimeWeather1 = DateTimeWeather.builder()
        .id(id)
        .city(city)
        .timestamp(timestamp)
        .temperature(temperature)
        .minTemp(minTemp)
        .maxTemp(maxTemp)
        .build();
    long id2 = 2;
    DateTimeWeather dateTimeWeather2 = DateTimeWeather.builder()
        .id(id)
        .city(city)
        .timestamp(timestamp)
        .temperature(temperature)
        .minTemp(minTemp)
        .maxTemp(maxTemp)
        .build();
    List<DateTimeWeather> dateTimeWeathers = Stream.of(dateTimeWeather1, dateTimeWeather2)
        .collect(Collectors.toList());
    when(repository.findByCityName(any()))
        .thenReturn(Stream.of(dateTimeWeather1, dateTimeWeather2).collect(Collectors.toList()));

    //when
    List<DateTimeWeather> foundDateTimeWeather = service.findByCityName(cityName);

    //then
    Assertions.assertEquals(dateTimeWeathers.size(), foundDateTimeWeather.size());
    Assertions.assertEquals(Iterables.getFirst(dateTimeWeathers, null),
        Iterables.getFirst(dateTimeWeathers, null));
    Assertions.assertEquals(Iterables.getLast(foundDateTimeWeather),
        Iterables.getLast(foundDateTimeWeather));
  }

  //TODO: Implement rest of DateTimeWeatherService methods, using the logic above
}
