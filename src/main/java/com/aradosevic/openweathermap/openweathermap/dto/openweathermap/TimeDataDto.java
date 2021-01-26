package com.aradosevic.openweathermap.openweathermap.dto.openweathermap;

import com.aradosevic.openweathermap.openweathermap.dto.openweathermap.MainTimeDataDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class TimeDataDto {

  @JsonProperty("dt")
  private Long timestamp;

  private MainTimeDataDto main;
}
