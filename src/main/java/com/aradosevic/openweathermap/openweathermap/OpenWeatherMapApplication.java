package com.aradosevic.openweathermap.openweathermap;

import com.aradosevic.openweathermap.openweathermap.configuration.properties.ClientAppProperties;
import com.aradosevic.openweathermap.openweathermap.dto.OpenWeatherAppDto;
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
//    URL url = null;
//    try {
//      url = new URL("http://example.com");
//      HttpURLConnection con = (HttpURLConnection) url.openConnection();
//      con.setRequestMethod("GET");
//
//      Map<String, String> parameters = new HashMap<>();
//      parameters.put("q", "London");
//      parameters.put("appid", "3fac93bd1dbf55ba44357d4ae9cd09de");
//      parameters.put("units", "metric");
//
//      con.setDoOutput(true);
//      DataOutputStream out = new DataOutputStream(con.getOutputStream());
//      out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
//      out.flush();
//      out.close();
//
//      con.setRequestProperty("Content-Type", "application/json");
//
//      con.setConnectTimeout(5000);
//      con.setReadTimeout(5000);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate, ClientAppProperties config) throws Exception {
        return args -> {
            OpenWeatherAppDto city1 = restTemplate.getForObject(
                    "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid=3fac93bd1dbf55ba44357d4ae9cd09de&units=metric",
                    OpenWeatherAppDto.class, config.getCity1());
            log.info(city1.toString());

            OpenWeatherAppDto city2 = restTemplate.getForObject(
                    "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid=3fac93bd1dbf55ba44357d4ae9cd09de&units=metric",
                    OpenWeatherAppDto.class, config.getCity2());
            log.info(city2.toString());

            OpenWeatherAppDto city3 = restTemplate.getForObject(
                    "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid=3fac93bd1dbf55ba44357d4ae9cd09de&units=metric",
                    OpenWeatherAppDto.class, config.getCity3());
            log.info(city3.toString());
        };
    }

}
