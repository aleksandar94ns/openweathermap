package com.aradosevic.openweathermap.openweathermap.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.WeatherController;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.request.CitiesAndDatesDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.request.SpecificDatesDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.dto.response.CityDto;
import com.aradosevic.openweathermap.openweathermap.api.v1.controller.weather.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WeatherController.class)
@AutoConfigureMockMvc
public class WeatherControllerTest {

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
      MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
      StandardCharsets.UTF_8);
  private static final String RESOURCE_ENDPOINT = "/api/v1/weather";
  @MockBean
  private WeatherService weatherService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void weather_Cities_ShouldSucceed_WhenValidParametersAreSent() throws Exception {

    when(weatherService.getAllCities())
        .thenReturn(Stream.of(CityDto.builder().build()).collect(Collectors.toList()));

    mockMvc.perform(get(RESOURCE_ENDPOINT + "/cities"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void weather_GetAverageForGivenCities_ShouldSucceed_WhenValidParametersAreSent()
      throws Exception {

    //given
    CitiesAndDatesDto contentDto = new CitiesAndDatesDto();
    List<String> cities = Stream.of("London", "Tokyo").collect(Collectors.toList());
    contentDto.setCities(cities);

    SpecificDatesDto specificDatesDto = new SpecificDatesDto();
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();

    specificDatesDto.setStartDate(calendar.getTime().getTime());
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, 3);
    specificDatesDto.setEndDate(calendar.getTime().getTime());

    contentDto.setDatesDto(specificDatesDto);

    //when
    when(weatherService.getAverageForCitiesBetweenDates(anyList(), anyLong(), anyLong()))
        .thenReturn(new ArrayList<>());

    //then
    mockMvc.perform(post(RESOURCE_ENDPOINT + "/average-between")
        .contentType(APPLICATION_JSON_UTF8)
        .content(asJsonString(contentDto)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void weather_GetAverageForGivenCities_ShouldFail_WhenValidParametersAreNotSent()
      throws Exception {
    //given & when
    when(weatherService.getAverageForCitiesBetweenDates(anyList(), anyLong(), anyLong()))
        .thenReturn(new ArrayList<>());

    // then
    mockMvc.perform(post(RESOURCE_ENDPOINT + "/average-between"))
        .andDo(print())
        .andExpect(status().isInternalServerError());
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
