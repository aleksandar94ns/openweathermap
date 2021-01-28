package com.aradosevic.openweathermap.openweathermap.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private Long id;
    private String parameter;

    public NotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public NotFoundException(String message, String parameter) {
        super(message);
        this.parameter = parameter;
    }
}
