package com.terena.interview.solution.weather.services.impl;

import com.terena.interview.solution.weather.common.config.WeatherServiceApplicationConfig;
import com.terena.interview.solution.weather.common.entity.WeatherQueryResult;
import com.terena.interview.solution.weather.common.model.QueryResultDTO;
import com.terena.interview.solution.weather.repositories.WeatherQueryResponseRepository;
import com.terena.interview.solution.weather.services.HistoryWeatherService;
import com.terena.interview.solution.weather.services.util.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * History Weather Service layer
 * This service is responsible for two things:
 * 1. Store weather data that was queried based on city/location recently to DB.
 * 2. Retrieves historical data from DB based on city/location and history count configuration.
 */
@Slf4j
@Service
public class HistoryWeatherServiceImpl implements HistoryWeatherService {

    private WeatherServiceApplicationConfig weatherServiceApplicationConfig;
    private WeatherQueryResponseRepository weatherQueryResponseRepository;

    public HistoryWeatherServiceImpl(WeatherQueryResponseRepository weatherQueryResponseRepository, WeatherServiceApplicationConfig config) {
        this.weatherQueryResponseRepository = weatherQueryResponseRepository;
        this.weatherServiceApplicationConfig = config;
    }

    /**
     * This method stores weather data that was queried based on city/location recently to DB.
     * @param location - city/location based on current weather was queried
     * @param queryResultDTO - query result object
     * @return {@link QueryResultDTO} which was persisted in DB
     */
    @Transactional
    @Override
    public QueryResultDTO storeCurrentWeatherResponseToDB(String location, QueryResultDTO queryResultDTO) {

        WeatherQueryResult persistedWeatherQueryResult = weatherQueryResponseRepository.save(ServiceUtil.convertToWeatherQueryResult(queryResultDTO));
        log.info("Query Response is stored into DB for future reference");

        return ServiceUtil.convertToQueryResultDTO(persistedWeatherQueryResult);
    }

    /**
     * This method retrieves historical data from DB based on city/location and history count configuration.
     * @param location - city/location based on current weather was queried
     * @return re {@link List<QueryResultDTO>} a recent list of stored query results
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<QueryResultDTO> retrieveHistoricalDataFromDB(String location) {

        log.info(String.format("Retrieving historical query data from DB for location %s: ", location));
        Sort sort = Sort.sort(WeatherQueryResult.class).by(WeatherQueryResult::getCreatedDate).descending();
        PageRequest pageRequest = PageRequest.of(0, Integer.valueOf(weatherServiceApplicationConfig.getHistoryCount()), sort);
        String city = ServiceUtil.getCityName(location);

        List<WeatherQueryResult> recentQueryResponses = weatherQueryResponseRepository.findRecentResponsesByQueryCity(city, pageRequest);
        List<QueryResultDTO> queryResultDTOS = ServiceUtil.convertToQueryResultDTOList(recentQueryResponses);
        log.info(String.format("Historical query data for location %s has been retrieved successfully %s: ", location, recentQueryResponses));

        return queryResultDTOS;
    }
}
