package com.aradosevic.openweathermap.openweathermap.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "date_time_weather")
public class DateTimeWeather {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "city", nullable = false)
  private City city;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;

  private Double temperature;
  private Double minTemp;
  private Double maxTemp;
}
