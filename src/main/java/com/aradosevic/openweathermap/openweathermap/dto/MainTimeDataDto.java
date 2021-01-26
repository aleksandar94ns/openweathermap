package com.aradosevic.openweathermap.openweathermap.dto;

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
}
