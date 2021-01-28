package com.aradosevic.openweathermap.openweathermap.openweather;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import com.aradosevic.openweathermap.openweathermap.openweather.dto.MainTimeDataDto;
import com.aradosevic.openweathermap.openweathermap.openweather.dto.OpenWeatherAppDto;
import com.aradosevic.openweathermap.openweathermap.openweather.dto.TimeDataDto;
import com.aradosevic.openweathermap.openweathermap.service.CityService;
import com.aradosevic.openweathermap.openweathermap.service.DateTimeWeatherService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherMapServiceTest {

  @InjectMocks
  OpenWeatherMapService openWeatherMapService;

  @Mock
  CityService cityService;

  @Mock
  DateTimeWeatherService dateTimeWeatherService;

  @Mock
  RestTemplateBuilder restTemplateBuilder;

  @Mock
  ClientAppProperties clientAppProperties;

  @Test
  void testPopulation() {
    //when
    String cityName1 = "London";
    when(clientAppProperties.getCity1()).thenReturn(cityName1);
    String cityName2 = "Tokyo";
    when(clientAppProperties.getCity2()).thenReturn(cityName2);
    String cityName3 = "Belgrade";
    when(clientAppProperties.getCity3()).thenReturn(cityName3);

    City city1 = City.builder().name(cityName1).build();
    City city2 = City.builder().name(cityName2).build();
    City city3 = City.builder().name(cityName3).build();
    List<City> cityList = Stream.of(city1, city2, city3).collect(Collectors.toList());

    when(cityService.save(any())).thenReturn(city1);

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    OpenWeatherAppDto dto = new OpenWeatherAppDto();
    dto.setCod("cod");
    dto.setMessage("message");
    dto.setCnt(10);

    List<TimeDataDto> dataDtos = new ArrayList<>();
    Date date = new Date();

    DateTimeWeather dateTimeWeather = new DateTimeWeather();

    for (int i = 0; i < 10; i++) {
      TimeDataDto timeDataDto = new TimeDataDto();

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.HOUR_OF_DAY, i * 3);
      long timestamp = calendar.getTime().getTime();
      timeDataDto.setTimestamp(timestamp);

      MainTimeDataDto mainTimeData = new MainTimeDataDto();
      double temperature = i;
      double maxTemp = i + 3.0;
      double minTemp = i - 3.0;
      mainTimeData.setTemperature(temperature);
      mainTimeData.setMaxTemp(maxTemp);
      mainTimeData.setMinTemp(minTemp);

      timeDataDto.setMain(mainTimeData);

      dataDtos.add(timeDataDto);

      dateTimeWeather = DateTimeWeather.builder()
          .id(i)
          .city(city1)
          .timestamp(calendar.getTime())
          .temperature(temperature)
          .minTemp(minTemp)
          .maxTemp(maxTemp)
          .build();
    }

    dto.setTimeData(dataDtos);

    when(dateTimeWeatherService.save(any())).thenReturn(dateTimeWeather);

    when(restTemplate.getForObject(any(), any(), any(), any()))
        .thenReturn(dto);

    //when
    openWeatherMapService.populateData();

    //then
    verify(cityService, times(cityList.size())).save(any());
    verify(dateTimeWeatherService, times(cityList.size() * dataDtos.size()))
        .save(any());
  }
}
