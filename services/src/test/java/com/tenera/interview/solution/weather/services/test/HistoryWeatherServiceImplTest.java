package com.tenera.interview.solution.weather.services.test;

import com.terena.interview.solution.weather.common.config.WeatherServiceApplicationConfig;
import com.terena.interview.solution.weather.common.entity.WeatherQueryResult;
import com.terena.interview.solution.weather.common.model.QueryResultDTO;
import com.terena.interview.solution.weather.repositories.WeatherQueryResponseRepository;
import com.terena.interview.solution.weather.services.HistoryWeatherService;
import com.terena.interview.solution.weather.services.impl.HistoryWeatherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HistoryWeatherServiceImplTest {

    private WeatherServiceApplicationConfig weatherServiceApplicationConfig;
    private WeatherQueryResponseRepository weatherQueryResponseRepository;
    private HistoryWeatherService historyWeatherService;

    @BeforeEach
    public void setUp() {
        weatherQueryResponseRepository = mock(WeatherQueryResponseRepository.class);
        weatherServiceApplicationConfig = mock(WeatherServiceApplicationConfig.class);
        historyWeatherService = new HistoryWeatherServiceImpl(weatherQueryResponseRepository, weatherServiceApplicationConfig);
    }

    @Test
    public void storeCurrentWeatherResponseToDBTest() {

        WeatherQueryResult outWeatherQueryResult = createWeatherQueryResult();
        QueryResultDTO inQueryResultDTO = createQueryResultDTO();

        when(weatherQueryResponseRepository.save(any())).thenReturn(outWeatherQueryResult);
        QueryResultDTO savedQueryResultDTO = historyWeatherService.storeCurrentWeatherResponseToDB("Berlin", inQueryResultDTO);

        assertNotNull(savedQueryResultDTO);
        assertEquals(outWeatherQueryResult.getQueryCity(), savedQueryResultDTO.getQueryCity());
        assertEquals(outWeatherQueryResult.getResponseTemp(), savedQueryResultDTO.getResponseTemp());
        assertEquals(outWeatherQueryResult.getResponsePressure(), savedQueryResultDTO.getResponsePressure());
        assertTrue(savedQueryResultDTO.getResponseUmbrella());

    }

    @Test
    public void retrieveHistoricalDataFromDBTest() {

        List<WeatherQueryResult> outWeatherQueryResultList = createWeatherQueryResultList();

        when(weatherServiceApplicationConfig.getHistoryCount()).thenReturn("5");
        when(weatherQueryResponseRepository.findRecentResponsesByQueryCity(any(),any())).thenReturn(outWeatherQueryResultList);
        List<QueryResultDTO> queryResultDTOListFromDB = historyWeatherService.retrieveHistoricalDataFromDB("Berlin");

        assertNotNull(queryResultDTOListFromDB);
        assertEquals(outWeatherQueryResultList.size(), queryResultDTOListFromDB.size());
        assertEquals(outWeatherQueryResultList.size(), queryResultDTOListFromDB.stream()
                .filter(queryResultDTO -> queryResultDTO.getQueryCity().equalsIgnoreCase("Berlin")).collect(Collectors.toList()).size());
    }

    private List<WeatherQueryResult> createWeatherQueryResultList() {

        List<WeatherQueryResult> weatherQueryResultList = new ArrayList<>();

        WeatherQueryResult weatherQueryResult1 = createWeatherQueryResult();
        WeatherQueryResult weatherQueryResult12 = createWeatherQueryResult();
        weatherQueryResultList.add(weatherQueryResult1);
        weatherQueryResultList.add(weatherQueryResult12);

        return weatherQueryResultList;
    }

    private QueryResultDTO createQueryResultDTO() {

        return QueryResultDTO.builder()
                .queryCity("Berlin")
                .responseTemp(12.56)
                .responsePressure(1004.8)
                .responseUmbrella(true)
                .build();
    }

    private WeatherQueryResult createWeatherQueryResult() {

        return WeatherQueryResult.builder()
                .queryCity("Berlin")
                .responseTemp(12.56)
                .responsePressure(1004.8)
                .responseUmbrella(true)
                .build();
    }
}
