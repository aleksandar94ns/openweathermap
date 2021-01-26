package com.aradosevic.openweathermap.openweathermap;

import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.domain.City;
import com.aradosevic.openweathermap.openweathermap.dto.OpenWeatherAppDto;
import com.aradosevic.openweathermap.openweathermap.repository.CityRepository;
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
    public CommandLineRunner run(RestTemplate restTemplate, ClientAppProperties config, CityRepository cityRepository) throws Exception {
        return args -> {
            OpenWeatherAppDto city1 = restTemplate.getForObject(
                    "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid=3fac93bd1dbf55ba44357d4ae9cd09de&units=metric",
                    OpenWeatherAppDto.class, config.getCity1());
            log.info(city1.toString());
            City city11 = new City();
            city11.setName(config.getCity1());
            cityRepository.save(city11);

            OpenWeatherAppDto city2 = restTemplate.getForObject(
                    "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid=3fac93bd1dbf55ba44357d4ae9cd09de&units=metric",
                    OpenWeatherAppDto.class, config.getCity2());
            log.info(city2.toString());
            City city22 = new City();
            city22.setName(config.getCity2());
            cityRepository.save(city22);

            OpenWeatherAppDto city3 = restTemplate.getForObject(
                    "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid=3fac93bd1dbf55ba44357d4ae9cd09de&units=metric",
                    OpenWeatherAppDto.class, config.getCity3());
            log.info(city3.toString());
            City city33 = new City();
            city33.setName(config.getCity3());
            cityRepository.save(city33);
        };
    }

}
