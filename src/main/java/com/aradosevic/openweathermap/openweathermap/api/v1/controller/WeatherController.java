package com.aradosevic.openweathermap.openweathermap.api.v1.controller;

import com.aradosevic.openweathermap.openweathermap.dto.CityWeatherDto;
import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage.DefaultMessages;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
@Log4j2
public class WeatherController {

  @ApiOperation(value = "Get weather for all cities")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Weathers",
              response = CityWeatherDto.class,
              responseContainer = "List")
          ,
          @ApiResponse(code = 500, message = DefaultMessages.INTERNAL_SERVER_ERROR)
      })
  @GetMapping
  public ResponseEntity<CityWeatherDto> getWeatherForAllCities() {
    return ResponseEntity.ok(CityWeatherDto.builder().build());
  }

}
