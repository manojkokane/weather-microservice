package com.terena.interview.solution.weather.common.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Application Configuration class - for now loads the configuration from application.properties/yaml
 */
@Configuration
@Getter
@NoArgsConstructor
public class WeatherServiceApplicationConfig {

    @Value("${weatherService.config.weatherDataApi.version:2.5}")
    private String weatherDataApiVersion;

    @Value("${weatherService.config.weatherDataApi.unit:metric}")
    private String weatherDataApiUnit;

    @Value("${weatherService.config.weatherDataApi.current.weatherDataApiBaseUrl}")
    private String currentWeatherDataApiBaseUrl;

    @Value("${weatherService.config.weatherDataApi.current.weatherDataApiEndpoint}")
    private String currentWeatherDataApiEndpoint;

    @Value("${weatherService.config.weatherDataApi.current.httpMethod}")
    private String currentWeatherDataApiHttpMethod;

    @Value("${weatherService.config.weatherDataApi.history.count:5}")
    private String historyCount;
}
