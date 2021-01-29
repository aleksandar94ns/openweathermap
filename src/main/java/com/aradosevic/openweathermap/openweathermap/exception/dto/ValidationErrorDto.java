package com.aradosevic.openweathermap.openweathermap.exception.dto;

import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.FieldError;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorDto {

  @ApiModelProperty(required = true)
  @NonNull
  private String field;

  @ApiModelProperty(required = true)
  @NonNull
  private String rejectedValue;

  @ApiModelProperty(required = true)
  @NonNull
  private String messageKey;

  @ApiModelProperty(required = true)
  @NonNull
  private String defaultMessage;

  public static ValidationErrorDto fromFieldError(FieldError fieldError) {
    return ValidationErrorDto.builder()
        .field(fieldError.getField())
        .rejectedValue(
            Optional.ofNullable(fieldError.getRejectedValue())
                .map(Object::toString)
                .orElse(""))
        .messageKey(fieldError.getDefaultMessage())
        .defaultMessage(
            ErrorMessage.from(fieldError.getDefaultMessage()).getDefaultMessage())
        .build();
  }
}