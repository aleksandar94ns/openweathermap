package com.aradosevic.openweathermap.openweathermap.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class that represents city in database and domain generally.
 * Contains name of the city that is being searched.
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "city")
public class City {

  @OneToMany(mappedBy = "city")
  private List<DateTimeWeather> dateTimeWeathers;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  //TODO: Extend this to support cityId's from https://openweathermap.org/ besides names
}
