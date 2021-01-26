package com.aradosevic.openweathermap.openweathermap.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "date_time_weather")
public class DateTimeWeather {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city", nullable = false)
    private City city;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long timestamp;

    private Double temperature;
    private Double minTemp;
    private Double maxTemp;
}
