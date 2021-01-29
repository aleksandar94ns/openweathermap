package com.aradosevic.openweathermap.openweathermap.repository;

import com.aradosevic.openweathermap.openweathermap.domain.DateTimeWeather;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//TODO: Make these optional, requires more exception handling
//TODO: Would use DateTimeWeatherDb class instead of DateTimeWeather, but would overcomplicate the project
@Repository
public interface DateTimeWeatherRepository extends JpaRepository<DateTimeWeather, Long> {

  //TODO: Can be deleted
  List<DateTimeWeather> findByCityName(String cityName);

  //TODO: Finish pageable implementation
  Page<DateTimeWeather> findByCityName(String cityName, Pageable pageable);

  /**
   * Query to find all {@link DateTimeWeather} instances where given parameters match
   *
   * @param cityName  {@link String} used to filter by {@link com.aradosevic.openweathermap.openweathermap.domain.City}
   *                  name
   * @param startDate used to search for all instances that occur after given date {@link Date}
   * @param endDate   used to search for all instances that occur before given date {@link Date}
   * @return {@link DateTimeWeather} that matches given query parameters
   */
  List<DateTimeWeather> findByCityNameAndTimestampBetween(String cityName, Date startDate,
      Date endDate);

  //TODO: Document code below, simpler versions from the query above
  List<DateTimeWeather> findByCityNameAndTimestampBefore(String cityName, Date endDate);

  List<DateTimeWeather> findByCityNameAndTimestampAfter(String cityName, Date startDate);
}
