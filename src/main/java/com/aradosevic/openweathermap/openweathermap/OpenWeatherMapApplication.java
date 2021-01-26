package com.aradosevic.openweathermap.openweathermap;

import com.aradosevic.openweathermap.openweathermap.dto.OpenWeatherAppDto;
import com.aradosevic.openweathermap.openweathermap.openweather.ParameterStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
  public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
    return args -> {
      OpenWeatherAppDto quote = restTemplate.getForObject(
              "https://api.openweathermap.org/data/2.5/forecast?q=London&appid=3fac93bd1dbf55ba44357d4ae9cd09de&units=metric", OpenWeatherAppDto.class);
      log.info(quote.toString());
    };
  }

}
