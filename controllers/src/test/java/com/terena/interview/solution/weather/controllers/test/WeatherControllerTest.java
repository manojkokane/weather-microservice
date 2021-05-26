package com.terena.interview.solution.weather.controllers.test;

import com.terena.interview.solution.weather.common.model.CurrentWeatherDataDTO;
import com.terena.interview.solution.weather.common.model.QueryResultDTO;
import com.terena.interview.solution.weather.common.model.WeatherConditionDTO;
import com.terena.interview.solution.weather.common.model.WeatherDataDTO;
import com.terena.interview.solution.weather.controllers.WeatherController;
import com.terena.interview.solution.weather.models.WeatherHistoryResponse;
import com.terena.interview.solution.weather.models.WeatherResponse;
import com.terena.interview.solution.weather.services.HistoryWeatherService;
import com.terena.interview.solution.weather.services.OpenWeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherControllerTest {

    private OpenWeatherService openWeatherService;
    private HistoryWeatherService historyWeatherService;
    private WeatherController weatherController;

    @BeforeEach
    public void setUp() {
        openWeatherService = mock(OpenWeatherService.class);
        historyWeatherService = mock(HistoryWeatherService.class);
        weatherController = new WeatherController(openWeatherService, historyWeatherService);
    }

    @Test
    public void getCurrentWeatherForCityTest() {
        CurrentWeatherDataDTO openWeatherServiceResponse = createCurrentWeatherDataDTO();
        when(openWeatherService.retrieveCurrentDataFromOpenWeather(any(), any())).thenReturn(openWeatherServiceResponse);
        ResponseEntity<WeatherResponse> weatherResponseResponseEntity = weatherController.getCurrentWeatherForCity("Berlin", "df454gfdg565dffgd55");

        HttpStatus httpStatus = weatherResponseResponseEntity.getStatusCode();
        WeatherResponse weatherResponse = weatherResponseResponseEntity.getBody();

        assertEquals(200, httpStatus.value());
        assertEquals(Double.valueOf(12.7), weatherResponse.getTemp());
        assertEquals(Double.valueOf(1004.3), weatherResponse.getPressure());
        assertTrue(weatherResponse.isUmbrella());
    }

    @Test
    public void getWeatherHistoryForCityTest() {
        List<QueryResultDTO> queryResultDTOList = createHistoryWeatherResponse();
        when(historyWeatherService.retrieveHistoricalDataFromDB(any())).thenReturn(queryResultDTOList);
        ResponseEntity<WeatherHistoryResponse> weatherHistoryResponseResponseEntity = weatherController.getWeatherHistoryForCity("Berlin");

        HttpStatus httpStatus = weatherHistoryResponseResponseEntity.getStatusCode();
        WeatherHistoryResponse weatherHistoryResponse = weatherHistoryResponseResponseEntity.getBody();

        assertEquals(200, httpStatus.value());
        assertEquals(Double.valueOf(10.3), weatherHistoryResponse.getAvgTemp());
        assertEquals(Double.valueOf(1005.6), weatherHistoryResponse.getAvgPressure());
        assertEquals(2, weatherHistoryResponse.getHistory().size());
    }

    private CurrentWeatherDataDTO createCurrentWeatherDataDTO() {

        WeatherConditionDTO weatherConditionDTO = WeatherConditionDTO.builder()
                .code(210)
                .codeName("Thunderstorm")
                .build();
        List<WeatherConditionDTO> weatherConditionDTOList = new ArrayList<>();
        weatherConditionDTOList.add(weatherConditionDTO);

        WeatherDataDTO weatherDataDTO = WeatherDataDTO.builder()
                .temp(12.7)
                .airPressure(1004.3)
                .build();

        return CurrentWeatherDataDTO.builder()
                .weatherDataDTO(weatherDataDTO)
                .weatherConditionDTOS(weatherConditionDTOList)
                .build();
    }

    private List<QueryResultDTO> createHistoryWeatherResponse() {

        QueryResultDTO queryResultDTO1 = QueryResultDTO.builder()
                .responseTemp(10.3)
                .responsePressure(1005.6)
                .responseUmbrella(false)
                .build();

        QueryResultDTO queryResultDTO2 = QueryResultDTO.builder()
                .responseTemp(10.3)
                .responsePressure(1005.6)
                .responseUmbrella(true)
                .build();
        List<QueryResultDTO> queryResultDTOList = new ArrayList<>();
        queryResultDTOList.add(queryResultDTO1);
        queryResultDTOList.add(queryResultDTO2);

        return queryResultDTOList;
    }
}
