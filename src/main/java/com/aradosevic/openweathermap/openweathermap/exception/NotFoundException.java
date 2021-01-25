package com.aradosevic.openweathermap.openweathermap.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final Long id;

    public NotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }
}
