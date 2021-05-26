package com.terena.interview.solution.weather.services;


import com.terena.interview.solution.weather.common.model.CurrentWeatherDataDTO;

/**
 * OpenWeather service layer.
 * This service is responsible for fetching current weather data from OpenWeather 3rd party service
 */
public interface OpenWeatherService {

    /**
     * This is method will retrieve current weather from OpenWeather service API based on location provided
     * @param location - city name
     * @param xOpenWeatherApiKey - OpenWeather service API key
     * @return
     */
    CurrentWeatherDataDTO retrieveCurrentDataFromOpenWeather(String location, String xOpenWeatherApiKey);
}
