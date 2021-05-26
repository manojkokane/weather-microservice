package com.terena.interview.solution.weather.services;

import com.terena.interview.solution.weather.common.model.QueryResultDTO;

import java.util.List;

/**
 * History Weather Service layer
 * This service is responsible for two things:
 * 1. Store weather data that was queried based on city/location recently to DB.
 * 2. Retrieves historical data from DB based on city/location and history count configuration.
 */
public interface HistoryWeatherService {

    /**
     * This method stores weather data that was queried based on city/location recently to DB.
     * @param location - city/location based on current weather was queried
     * @param queryResultDTO - query result object
     * @return {@link QueryResultDTO} which was persisted in DB
     */
    QueryResultDTO storeCurrentWeatherResponseToDB(String location, QueryResultDTO queryResultDTO);

    /**
     * This method retrieves historical data from DB based on city/location and history count configuration.
     * @param location - city/location based on current weather was queried
     * @return re {@link List<QueryResultDTO>} a recent list of stored query results
     */
    List<QueryResultDTO> retrieveHistoricalDataFromDB(String location);
}
