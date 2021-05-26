package com.terena.interview.solution.weather.services.impl;

import com.terena.interview.solution.weather.common.config.WeatherServiceApplicationConfig;
import com.terena.interview.solution.weather.common.model.CurrentWeatherDataDTO;
import com.terena.interview.solution.weather.common.restclient.feign.FeignProxyClient;
import com.terena.interview.solution.weather.connectors.openwather.OpenWeatherConnector;
import com.terena.interview.solution.weather.services.OpenWeatherService;
import com.terena.interview.solution.weather.services.util.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * OpenWeather service layer.
 * This service is responsible for fetching current weather data from OpenWeather 3rd party service
 */
@Slf4j
@Service
public class OpenWeatherServiceImpl implements OpenWeatherService {

    private OpenWeatherConnector openWeatherConnector;
    private WeatherServiceApplicationConfig weatherServiceApplicationConfig;

    public OpenWeatherServiceImpl(OpenWeatherConnector connector, WeatherServiceApplicationConfig config) {
        this.openWeatherConnector = connector;
        this.weatherServiceApplicationConfig = config;
    }

    /**
     * This is method will retrieve current weather from OpenWeather service API based on location provided
     * @param location - city name
     * @param xOpenWeatherApiKey - OpenWeather service API key
     * @return
     */
    @Override
    public CurrentWeatherDataDTO retrieveCurrentDataFromOpenWeather(String location, String xOpenWeatherApiKey) {
        log.info("Retrieving current weather data for location: " + location);

        String openWeatherUrl = ServiceUtil.getOpenWeatherUrl(weatherServiceApplicationConfig.getCurrentWeatherDataApiBaseUrl(), weatherServiceApplicationConfig.getWeatherDataApiVersion());
        Map<String, Collection<String>> currentDataRequestQueries = ServiceUtil.getQueriesForCurrentData(location, xOpenWeatherApiKey, weatherServiceApplicationConfig);

        log.debug(String.format("OpenWeather Url for current weather data: %s ", openWeatherUrl));

        FeignProxyClient client = openWeatherConnector.connectTOCurrentDataProxyClient(openWeatherUrl, currentDataRequestQueries);
        CurrentWeatherDataDTO currentWeatherDataDTO = (CurrentWeatherDataDTO) client.execute();

        return currentWeatherDataDTO;
    }
}
