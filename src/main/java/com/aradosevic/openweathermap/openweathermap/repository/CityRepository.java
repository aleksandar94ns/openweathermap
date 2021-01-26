package com.aradosevic.openweathermap.openweathermap.repository;

import com.aradosevic.openweathermap.openweathermap.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByName(String name);
}
