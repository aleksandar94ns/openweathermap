package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather;

import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.request.CitiesAndDatesDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.request.DateDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.request.SpecificDatesDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.CityDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.service.WeatherService;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.DefaultMessages;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller used to map API endpoints for different weather forecasts and statistics
 */
@RestController
@RequestMapping("/api/v1/weather")
@Log4j2
public class WeatherController {

  private final WeatherService weatherService;

  @Autowired
  public WeatherController(WeatherService weatherService) {
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
    return ResponseEntity.ok(weatherService.getAllCities());
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
    return ResponseEntity.ok(weatherService.getCityTemperatures(cityName));
  }

  @GetMapping("/{cityName}/average")
  public ResponseEntity<CityDto> getCityAverage(@PathVariable String cityName) {
    return ResponseEntity.ok(weatherService.getCityAverage(cityName));
  }

  @PostMapping("/average-between")
  public ResponseEntity<List<CityDto>> getCityAverage(
      @RequestBody @Valid CitiesAndDatesDto citiesAndDatesDto) {
    return ResponseEntity.ok(weatherService
        .getAverageForCitiesBetweenDates(citiesAndDatesDto.getCities(),
            citiesAndDatesDto.getDatesDto().getStartDate(),
            citiesAndDatesDto.getDatesDto().getEndDate()));
  }
}
