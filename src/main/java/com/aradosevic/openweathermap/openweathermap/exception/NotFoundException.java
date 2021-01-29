package com.aradosevic.openweathermap.openweathermap.exception;

import lombok.Getter;

/**
 * Custom {@link RuntimeException} class used for custom exception messages within the application.
 */
@Getter
public class NotFoundException extends RuntimeException {

  private Long id;
  private String parameter;

  //TODO: Can be used for city id, would be generally used for not found exceptions where we search by ids.
  public NotFoundException(String message, Long id) {
    super(message);
    this.id = id;
  }

  public NotFoundException(String message, String parameter) {
    super(message);
    this.parameter = parameter;
  }
}
