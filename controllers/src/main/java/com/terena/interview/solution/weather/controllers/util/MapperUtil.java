package com.terena.interview.solution.weather.controllers.util;

import com.terena.interview.solution.weather.common.model.CurrentWeatherDataDTO;
import com.terena.interview.solution.weather.common.model.QueryResultDTO;
import com.terena.interview.solution.weather.models.WeatherHistoryResponse;
import com.terena.interview.solution.weather.models.WeatherResponse;
import com.terena.interview.solution.weather.services.util.ServiceUtil;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Utility class to map objects to and fro
 */
public class MapperUtil {

    public static WeatherResponse mapToWeatherResponse(final CurrentWeatherDataDTO currentWeatherDataDTO) {

        return new WeatherResponse()
                .temp(currentWeatherDataDTO.getWeatherDataDTO().getTemp())
                .pressure(currentWeatherDataDTO.getWeatherDataDTO().getAirPressure())
                .umbrella(ServiceUtil.canTakeUmbrella(currentWeatherDataDTO.getWeatherConditionDTOS()));
    }

    public static WeatherHistoryResponse mapToHistoricWeatherResponse(final List<QueryResultDTO> weatherQueryResponses) {

        OptionalDouble averageTemp = weatherQueryResponses.stream().mapToDouble(queryData -> queryData.getResponseTemp()).average();
        OptionalDouble averagePressure = weatherQueryResponses.stream().mapToDouble(queryData -> queryData.getResponsePressure()).average();

        return new WeatherHistoryResponse()
                .avgTemp(averageTemp.isPresent()?averageTemp.getAsDouble():0.0)
                .avgPressure(averagePressure.isPresent()?averagePressure.getAsDouble():0.0)
                .history(mapToWeatherResponseList(weatherQueryResponses));
    }

    private static List<WeatherResponse> mapToWeatherResponseList(final List<QueryResultDTO> weatherQueryResponses) {

        return weatherQueryResponses.stream()
                .map(weatherQueryResponse -> mapToWeatherResponse(weatherQueryResponse))
                .collect(Collectors.toList());
    }

    private static WeatherResponse mapToWeatherResponse(final QueryResultDTO weatherQueryResponse) {

        return new WeatherResponse()
                .temp(weatherQueryResponse.getResponseTemp())
                .pressure(weatherQueryResponse.getResponsePressure())
                .umbrella(weatherQueryResponse.getResponseUmbrella());
    }

    public static QueryResultDTO mapToQueryResultDTO(String location, WeatherResponse currentWeatherResponse) {

        return QueryResultDTO.builder()
                .queryCity(ServiceUtil.getCityName(location))
                .responseTemp(currentWeatherResponse.getTemp())
                .responsePressure(currentWeatherResponse.getPressure())
                .responseUmbrella(currentWeatherResponse.isUmbrella())
                .build();
    }
}
