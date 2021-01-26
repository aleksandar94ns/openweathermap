package com.aradosevic.openweathermap.openweathermap.domain;

import javax.persistence.*;

@Entity
public class DateTimeWeather {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city", nullable = false)
    private City city;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
