package com.aradosevic.openweathermap.openweathermap.exception.dto;

import com.aradosevic.openweathermap.openweathermap.exception.handler.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class ApiErrorDto {

  @ApiModelProperty(required = true)
  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();

  /**
   * Human readable error message
   */
  @NonNull
  @ApiModelProperty(required = true, example = ErrorMessage.Keys.RESOURCE_NOT_FOUND)
  private String defaultMessage;

  /**
   * Error message id
   *
   * @see ErrorMessage#getKey()
   */
  @ApiModelProperty(required = true, example = ErrorMessage.DefaultMessages.RESOURCE_NOT_FOUND)
  private String messageKey;

  @ApiModelProperty
  private List<ValidationErrorDto> fieldValidationErrors;

}
