package com.aradosevic.openweathermap.openweathermap.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecificDatesDto {

  @NotNull
  Long startDate;

  @NotNull
  Long endDate;
}
