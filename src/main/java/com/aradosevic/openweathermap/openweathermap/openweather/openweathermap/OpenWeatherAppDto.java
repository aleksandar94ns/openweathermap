package com.aradosevic.openweathermap.openweathermap.openweather.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
public class OpenWeatherAppDto {
    String cod;
    String message;
    Integer cnt;

    @JsonProperty("list")
    List<TimeDataDto> timeData;
}
