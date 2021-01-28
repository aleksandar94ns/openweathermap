package com.aradosevic.openweathermap.openweathermap.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

@Log4j2
@AllArgsConstructor
@Getter
public enum ErrorMessage {

    INTERNAL_SERVER_ERROR(Keys.INTERNAL_SERVER_ERROR, DefaultMessages.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND(Keys.RESOURCE_NOT_FOUND, DefaultMessages.RESOURCE_NOT_FOUND),
    BAD_REQUEST(Keys.BAD_REQUEST, DefaultMessages.BAD_REQUEST),

    CITY_NOT_FOUND(Keys.CITY_NOT_FOUND, DefaultMessages.CITY_NOT_FOUND),
    CITY_BY_NAME_NOT_FOUND(Keys.CITY_BY_NAME_NOT_FOUND, DefaultMessages.CITY_BY_NAME_NOT_FOUND);

    private final String key;
    private final String defaultMessage;

    public static ErrorMessage from(String key) {
        return Arrays.stream(ErrorMessage.values())
                .filter(errorMessage -> errorMessage.getKey().equals(key))
                .findFirst()
                .orElseGet(() -> {
                    log.error(String.format("No default message for key %s", key));
                    return INTERNAL_SERVER_ERROR;
                });
    }

    /**
     * Defines application wide error keys. Should be unique
     */
    @UtilityClass
    public static final class Keys {

        public static final String INTERNAL_SERVER_ERROR = "internal.server.error";
        public static final String RESOURCE_NOT_FOUND = "resource.not.found";
        public static final String BAD_REQUEST = "bad.request";

        public static final String CITY_NOT_FOUND = "city.not.found";
        public static final String CITY_BY_NAME_NOT_FOUND = "city.by.name.not.found";
    }

    /**
     * Provides default error messages for defined application wide error keys
     */
    @UtilityClass
    public static final class DefaultMessages {
        public static final String INTERNAL_SERVER_ERROR = "Internal server error";
        public static final String RESOURCE_NOT_FOUND = "Resource not found";
        public static final String BAD_REQUEST = "Bad request";

        public static final String CITY_NOT_FOUND = "City [id: {%s}] not found";
        public static final String CITY_BY_NAME_NOT_FOUND = "City [name: {%s}] not found";
    }
}


