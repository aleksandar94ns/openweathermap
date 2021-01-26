package com.aradosevic.openweathermap.openweathermap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class OpenWeatherAppDto {
    String cod;
    String message;
    Integer cnt;

    @JsonProperty("list")
    List<TimeDataDto> timeData;
}
