package com.aradosevic.openweathermap.openweathermap.dto.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
