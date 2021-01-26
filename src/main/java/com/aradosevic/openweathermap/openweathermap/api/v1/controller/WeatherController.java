package com.aradosevic.openweathermap.openweathermap.api.v1.controller;

import com.aradosevic.openweathermap.openweathermap.dto.CityDto;
import com.aradosevic.openweathermap.openweathermap.dto.TimeDataDto;
import com.aradosevic.openweathermap.openweathermap.dto.factory.CityDtoFactory;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.DefaultMessages;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/weather")
@Log4j2
public class WeatherController {

    private final CityRepository cityRepository;

    @Autowired
    public WeatherController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
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

}
