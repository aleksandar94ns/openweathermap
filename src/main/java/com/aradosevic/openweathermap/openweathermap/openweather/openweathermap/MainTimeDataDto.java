package com.aradosevic.openweathermap.openweathermap.openweather.openweathermap;

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
public class MainTimeDataDto {

    @JsonProperty("temp")
    Double temperature;

    @JsonProperty("temp_min")
    Double minTemp;

    @JsonProperty("temp_max")
    Double maxTemp;
}
