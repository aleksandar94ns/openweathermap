package com.aradosevic.openweathermap.openweathermap.repository;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateTimeWeatherRepository extends JpaRepository<DateTimeWeather, Long> {
}
