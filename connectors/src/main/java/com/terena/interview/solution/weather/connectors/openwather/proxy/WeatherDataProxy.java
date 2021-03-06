package com.terena.interview.solution.weather.connectors.openwather.proxy;

import com.terena.interview.solution.weather.common.constant.WeatherServiceConstant;
import com.terena.interview.solution.weather.common.model.CurrentWeatherDataDTO;
import feign.RequestLine;

/**
 * This is Feign proxy fot targeted 3rd party OpenWeather service.
 * This is specific to current weather data.
 */
public interface WeatherDataProxy {

    @RequestLine(WeatherServiceConstant.RESOURCE_OPEN_WEATHER_CURRENT)
    CurrentWeatherDataDTO retrieveCurrentWeatherData();
}
