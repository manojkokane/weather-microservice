package com.terena.interview.solution.weather.services.util;

import com.terena.interview.solution.weather.common.config.WeatherServiceApplicationConfig;
import com.terena.interview.solution.weather.common.constant.WeatherServiceConstant;
import com.terena.interview.solution.weather.common.entity.WeatherQueryResult;
import com.terena.interview.solution.weather.common.model.QueryResultDTO;
import com.terena.interview.solution.weather.common.model.WeatherConditionDTO;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service utility class
 * This class works as utility class Service layer
 */
@Slf4j
public class ServiceUtil {

    private static final String[] weatherConditionsForUmbrella = new String[]{"Thunderstorm", "Drizzle", "Rain"};

    public static Map<String, Collection<String>> getQueriesForCurrentData(String location, String xOpenWeatherApiKey, WeatherServiceApplicationConfig config) {

        Map<String, Collection<String>> queries = new HashMap<>();
        queries.put(WeatherServiceConstant.QUERY_PARAM_Q, new ArrayList<>(Collections.singleton(location)));
        queries.put(WeatherServiceConstant.QUERY_PARAM_UNITS, new ArrayList<>(Collections.singleton(config.getWeatherDataApiUnit())));
        queries.put(WeatherServiceConstant.QUERY_PARAM_APP_ID, new ArrayList<>(Collections.singleton(xOpenWeatherApiKey)));
        return queries;
    }

    public static String getOpenWeatherUrl(String baseUrl, String version) {
        String url = new StringBuilder(baseUrl)
                .append(WeatherServiceConstant.URL_SEPARATOR)
                .append(version)
                .toString();

        return url;
    }

    public static boolean canTakeUmbrella(List<WeatherConditionDTO> weatherConditionDTOS) {

        Long matchedCodes = weatherConditionDTOS.stream()
                .filter(weatherConditionDTO -> canTakeUmbrella(weatherConditionDTO.getCodeName()))
                .count();
        log.info(String.format("Verifying weather condition to decide on umbrella: %s codes matched", matchedCodes));

        return matchedCodes > 0;
    }

    private static boolean canTakeUmbrella(String codeName) {

        return Arrays.stream(weatherConditionsForUmbrella)
                .filter(condition -> condition.equalsIgnoreCase(codeName))
                .count() > 0;
    }

    public static String getCityName(String location) {
        return location.split(WeatherServiceConstant.LOCATION_VALUE_SEPARATOR)[0];
    }

    public static List<QueryResultDTO> convertToQueryResultDTOList(List<WeatherQueryResult> recentFiveQueryResponses) {
        return recentFiveQueryResponses.stream().map(weatherQueryResult -> convertToQueryResultDTO(weatherQueryResult)).collect(Collectors.toList());
    }

    public static QueryResultDTO convertToQueryResultDTO(WeatherQueryResult weatherQueryResult) {

        return QueryResultDTO.builder()
                .queryCity(weatherQueryResult.getQueryCity())
                .responseTemp(weatherQueryResult.getResponseTemp())
                .responsePressure(weatherQueryResult.getResponsePressure())
                .responseUmbrella(weatherQueryResult.getResponseUmbrella())
                .build();
    }

    public static WeatherQueryResult convertToWeatherQueryResult(QueryResultDTO queryResultDTO) {
        return WeatherQueryResult.builder()
                .queryCity(queryResultDTO.getQueryCity())
                .responseTemp(queryResultDTO.getResponseTemp())
                .responsePressure(queryResultDTO.getResponsePressure())
                .responseUmbrella(queryResultDTO.getResponseUmbrella())
                .createdDate(ZonedDateTime.now())
                .build();

    }
}
