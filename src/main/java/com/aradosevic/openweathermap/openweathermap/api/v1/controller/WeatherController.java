package com.aradosevic.openweathermap.openweathermap.api.v1.controller;

import com.aradosevic.openweathermap.openweathermap.dto.CityDto;
import com.aradosevic.openweathermap.openweathermap.dto.DateDto;
import com.aradosevic.openweathermap.openweathermap.dto.SpecificDatesDto;
import com.aradosevic.openweathermap.openweathermap.dto.factory.CityDtoFactory;
import com.aradosevic.openweathermap.openweathermap.exception.NotFoundException;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.DefaultMessages;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.Keys;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import com.aradosevic.openweathermap.openweathermap.service.WeatherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
@Log4j2
public class WeatherController {

  private final CityRepository cityRepository;
  private final DateTimeWeatherRepository dateTimeWeatherRepository;
  private final WeatherService weatherService;

  @Autowired
  public WeatherController(CityRepository cityRepository,
      DateTimeWeatherRepository dateTimeWeatherRepository,
      WeatherService weatherService) {
    this.cityRepository = cityRepository;
    this.dateTimeWeatherRepository = dateTimeWeatherRepository;
    this.weatherService = weatherService;
  }

  @ApiOperation(value = "Get weather for all cities")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Weathers",
              response = CityDto.class,
              responseContainer = "List")
          ,
          @ApiResponse(code = 500, message = DefaultMessages.INTERNAL_SERVER_ERROR)
      })
  @GetMapping("/cities")
  public ResponseEntity<List<CityDto>> getAvailableCities() {
    return ResponseEntity.ok(cityRepository.findAll().stream().map(CityDtoFactory::from)
        .collect(Collectors.toList()));
  }

  @GetMapping("/cities/average")
  public ResponseEntity<List<CityDto>> getAverageForAllCities() {
    return ResponseEntity.ok(weatherService.getAverageForAllCities());
  }

  @GetMapping("/cities/average/asc")
  public ResponseEntity<List<CityDto>> getAverageForAllCitiesAscending() {
    return ResponseEntity.ok(weatherService.getAverageForAllCities()
        .stream().sorted((Comparator.comparing(CityDto::getAverageTemp)))
        .collect(Collectors.toList()));
  }

  @GetMapping("/cities/average/desc")
  public ResponseEntity<List<CityDto>> getAverageForAllCitiesDescending() {
    return ResponseEntity.ok(weatherService.getAverageForAllCities()
        .stream().sorted((Comparator.comparing(CityDto::getAverageTemp))
            .reversed())
        .collect(Collectors.toList()));
  }

  @GetMapping("/{cityName}/average-dates-between")
  public ResponseEntity<CityDto> getAverageForSpecificDate(@PathVariable String cityName,
      @RequestBody @Valid SpecificDatesDto specificDatesDto) {
    return ResponseEntity.ok(weatherService.getCityAverageFromDates(specificDatesDto.getStartDate(),
        specificDatesDto.getEndDate(), cityName));
  }

  @GetMapping("/cities/average-dates-between")
  public ResponseEntity<List<CityDto>> getAverageForCitiesBetweenDates(
      @RequestBody @Valid SpecificDatesDto specificDatesDto) {
    return ResponseEntity
        .ok(weatherService.getCitiesAverageBetweenDates(specificDatesDto.getStartDate(),
            specificDatesDto.getEndDate()));
  }

  @GetMapping("/{cityName}/average-dates-before")
  public ResponseEntity<CityDto> getAverageBeforeDate(@PathVariable String cityName,
      @RequestBody @Valid DateDto dateDto) {
    return ResponseEntity.ok(weatherService.getCityAverageBeforeDate(dateDto.getDate(), cityName));
  }

  @GetMapping("/{cityName}/average-dates-after")
  public ResponseEntity<CityDto> getAverageAfterDate(@PathVariable String cityName,
      @RequestBody @Valid DateDto dateDto) {
    return ResponseEntity.ok(weatherService.getCityAverageAfterDate(dateDto.getDate(), cityName));
  }

  @GetMapping("/{cityName}/temp")
  public ResponseEntity<CityDto> getCityTemperature(@PathVariable String cityName) {
    return ResponseEntity.ok(CityDtoFactory.from(cityRepository.findByName(cityName)
        .orElseThrow(() -> new NotFoundException(Keys.CITY_BY_NAME_NOT_FOUND, cityName))
    ));
  }

  @GetMapping("/{cityName}/average")
  public ResponseEntity<CityDto> getCityAverage(@PathVariable String cityName) {
    return ResponseEntity.ok(weatherService.getCityAverage(cityName));
  }
}
