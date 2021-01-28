package com.aradosevic.openweathermap.openweathermap.exception.handler;

import com.aradosevic.openweathermap.openweathermap.exception.NotFoundException;
import com.aradosevic.openweathermap.openweathermap.exception.dto.ApiErrorDto;
import com.aradosevic.openweathermap.openweathermap.exception.dto.ValidationErrorDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  @Order(Ordered.HIGHEST_PRECEDENCE)
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleInternal(Exception e) {
    log.error("Internal server error occurred", e);
    ApiErrorDto apiErrorDto =
        ApiErrorDto.builder()
            .messageKey(e.getMessage())
            .defaultMessage(
                ErrorMessage.from(e.getMessage()).getDefaultMessage())
            .build();
    return new ResponseEntity<>(apiErrorDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiErrorDto> notFoundException(NotFoundException e) {
    log.warn("Not found: ", e);
    ApiErrorDto apiErrorDto =
        ApiErrorDto.builder()
            .messageKey(e.getMessage())
            .defaultMessage(
                e.getId() == null ?
                    String.format(ErrorMessage.from(e.getMessage()).getDefaultMessage(), e.getParameter())
                    : String
                        .format(ErrorMessage.from(e.getMessage()).getDefaultMessage(), e.getId()))
            .build();
    return new ResponseEntity<>(apiErrorDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorDto> methodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error("MethodArgumentNotValidException: ", e);
    BindingResult bindingResult = e.getBindingResult();
    List<ValidationErrorDto> validationErrors =
        bindingResult.getFieldErrors().stream()
            .map(ValidationErrorDto::fromFieldError)
            .collect(Collectors.toList());
    ApiErrorDto apiErrorDto =
        ApiErrorDto.builder()
            .fieldValidationErrors(validationErrors)
            .messageKey(ErrorMessage.Keys.BAD_REQUEST)
            .defaultMessage(ErrorMessage.from(ErrorMessage.Keys.BAD_REQUEST).getDefaultMessage())
            .build();
    return new ResponseEntity<>(apiErrorDto, HttpStatus.BAD_REQUEST);
  }

}