package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DateTimeWeatherDto {

  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
  private Date time;
  private Double temperature;
  private Double minTemp;
  private Double maxTemp;

}
