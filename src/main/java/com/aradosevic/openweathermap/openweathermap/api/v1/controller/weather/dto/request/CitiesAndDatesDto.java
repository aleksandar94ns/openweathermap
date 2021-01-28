package com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitiesAndDatesDto {

  @NotNull
  private List<String> cities;

  @Valid
  private SpecificDatesDto datesDto;
}
