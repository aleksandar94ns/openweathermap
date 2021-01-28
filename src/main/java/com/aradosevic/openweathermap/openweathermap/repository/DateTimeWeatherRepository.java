package com.aradosevic.openweathermap.openweathermap.repository;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateTimeWeatherRepository extends JpaRepository<DateTimeWeather, Long> {

  List<DateTimeWeather> findByCityName(String cityName);

  List<DateTimeWeather> findByCityNameAndTimestampBetween(String cityName, Date startDate,
      Date endDate);

  List<DateTimeWeather> findByCityNameAndTimestampBefore(String cityName, Date endDate);

  List<DateTimeWeather> findByCityNameAndTimestampAfter(String cityName, Date startDate);
}
