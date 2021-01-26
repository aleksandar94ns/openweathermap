package com.aradosevic.openweathermap.openweathermap;

import com.aradosevic.openweathermap.openweathermap.openweather.OpenWeatherMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OpenWeatherMapApplication {

    private static final Logger log = LoggerFactory.getLogger(OpenWeatherMapApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OpenWeatherMapApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(OpenWeatherMapService openWeatherMapService) {
        return args -> {
            openWeatherMapService.fetchData();
        };
    }

}
