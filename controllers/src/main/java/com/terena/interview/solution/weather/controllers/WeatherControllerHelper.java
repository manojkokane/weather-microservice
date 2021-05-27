package com.terena.interview.solution.weather.controllers;

import com.terena.interview.solution.weather.common.model.QueryResultDTO;
import com.terena.interview.solution.weather.services.HistoryWeatherService;
import lombok.extern.slf4j.Slf4j;

/**
 * Helper class for WeatherController
 */
@Slf4j
public class WeatherControllerHelper {
    /**
     * Sends Aync request to store query result into DB since end user should not hit performance just because of this DB call.
     * @param historyWeatherService
     * @param location
     * @param queryResultDTO
     */
    public static void storeCurrentWeatherResponseToDBAsync(HistoryWeatherService historyWeatherService, String location, QueryResultDTO queryResultDTO) {

        new Thread(() -> {

            log.info("Sending Async request to store query result into DB");
            historyWeatherService.storeCurrentWeatherResponseToDB(location, queryResultDTO);
            log.info("Async request to store query result into DB completed successfully");
        }).start();
    }
}
