package com.aradosevic.openweathermap.openweathermap.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class City {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
