package com.aradosevic.openweathermap.openweathermap.repository;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO: Would use CityDb class instead of City, but would overcomplicate the project
public interface CityRepository extends JpaRepository<City, Long> {

  Optional<City> findByName(String name);
}
