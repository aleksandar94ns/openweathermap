package com.aradosevic.openweathermap.openweathermap.api.v1.controller;

import com.aradosevic.openweathermap.openweathermap.dto.CityDto;
import com.aradosevic.openweathermap.openweathermap.dto.SpecificDatesDto;
import com.aradosevic.openweathermap.openweathermap.dto.factory.CityDtoFactory;
import com.aradosevic.openweathermap.openweathermap.dto.openweathermap.TimeDataDto;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.DefaultMessages;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import com.aradosevic.openweathermap.openweathermap.repository.DateTimeWeatherRepository;
import com.aradosevic.openweathermap.openweathermap.service.WeatherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/weather")
@Log4j2
public class WeatherController {

    private final CityRepository cityRepository;
    private final DateTimeWeatherRepository dateTimeWeatherRepository;
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(CityRepository cityRepository, DateTimeWeatherRepository dateTimeWeatherRepository,
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
                            response = TimeDataDto.class,
                            responseContainer = "List")
                    ,
                    @ApiResponse(code = 500, message = DefaultMessages.INTERNAL_SERVER_ERROR)
            })
    @GetMapping
    public ResponseEntity<TimeDataDto> getWeatherForAllCities() {
        return ResponseEntity.ok(TimeDataDto.builder().build());
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityDto>> getAvailableCities() {
        return ResponseEntity.ok(cityRepository.findAll().stream().map(CityDtoFactory::from).collect(Collectors.toList()));
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

    @GetMapping("/{cityName}/average")
    public ResponseEntity<List<CityDto>> getAverageForSpecificDate(@PathVariable String cityName,
                                                                   @RequestBody @Valid SpecificDatesDto specificDatesDto) {
        return ResponseEntity.ok(weatherService.getAverageForAllCities()
                .stream().sorted((Comparator.comparing(CityDto::getAverageTemp))
                        .reversed())
                .collect(Collectors.toList()));
    }

    @GetMapping("/{cityName}/temp")
    public ResponseEntity<CityDto> getCityTemperature(@PathVariable String cityName) {
        return ResponseEntity.ok(CityDtoFactory.from(cityRepository.findByName(cityName)));
    }

    @GetMapping("/{cityName}/average")
    public ResponseEntity<CityDto> getCityAverage(@PathVariable String cityName) {
        return ResponseEntity.ok(weatherService.getCityAverage(cityName));
    }
}
