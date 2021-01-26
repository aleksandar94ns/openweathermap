package com.aradosevic.openweathermap.openweathermap.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DatesDto {

    @NotNull
    Long date;
}
