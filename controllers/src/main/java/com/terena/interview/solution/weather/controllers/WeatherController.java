package com.terena.interview.solution.weather.controllers;

import com.terena.interview.solution.weather.api.WeatherApi;
import com.terena.interview.solution.weather.common.model.CurrentWeatherDataDTO;
import com.terena.interview.solution.weather.controllers.util.MapperUtil;
import com.terena.interview.solution.weather.models.WeatherHistoryResponse;
import com.terena.interview.solution.weather.models.WeatherResponse;
import com.terena.interview.solution.weather.services.OpenWeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Weather API Controller class
 * This is an entry point for all the incoming requests to weather API
 */
@RestController
@Validated
public class WeatherController implements WeatherApi {

    private OpenWeatherService openWeatherService;

    public WeatherController(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }

    /**
     * This method takes city/location and OpenWeather service API key as input and delegates task of retrieving current weather data to {@link OpenWeatherService}
     * @param location
     * @param xOpenWeatherApiKey
     * @return {@link WeatherResponse} containing current weather data for requested city/location
     */
    @Override
    public ResponseEntity<WeatherResponse> getCurrentWeatherForCity(@NotNull @Pattern(regexp = "[a-zA-Z,]+") @Size(min = 2, max = 50) @Valid String location, String xOpenWeatherApiKey) {
        CurrentWeatherDataDTO currentWeatherDataDTO = openWeatherService.retrieveCurrentDataFromOpenWeather(location, xOpenWeatherApiKey);
        WeatherResponse currentWeatherResponse = MapperUtil.mapToWeatherResponse(currentWeatherDataDTO);
        return ResponseEntity.ok(currentWeatherResponse);
    }

    @Override
    public ResponseEntity<WeatherHistoryResponse> getWeatherHistoryForCity(@NotNull @Pattern(regexp = "[a-zA-Z,]+") @Size(min = 2, max = 50) @Valid String location) {
        return null;
    }
}