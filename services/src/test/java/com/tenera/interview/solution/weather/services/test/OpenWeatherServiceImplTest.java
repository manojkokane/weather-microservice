package com.tenera.interview.solution.weather.services.test;

import com.terena.interview.solution.weather.common.config.WeatherServiceApplicationConfig;
import com.terena.interview.solution.weather.common.model.CurrentWeatherDataDTO;
import com.terena.interview.solution.weather.common.model.WeatherConditionDTO;
import com.terena.interview.solution.weather.common.model.WeatherDataDTO;
import com.terena.interview.solution.weather.common.restclient.feign.FeignProxyClient;
import com.terena.interview.solution.weather.connectors.openwather.OpenWeatherConnector;
import com.terena.interview.solution.weather.services.OpenWeatherService;
import com.terena.interview.solution.weather.services.impl.OpenWeatherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OpenWeatherServiceImplTest {

    private OpenWeatherConnector openWeatherConnector;
    private OpenWeatherService openWeatherService;
    private WeatherServiceApplicationConfig config;

    @BeforeEach
    public void setUp() {
        openWeatherConnector = mock(OpenWeatherConnector.class);
        config = mock(WeatherServiceApplicationConfig.class);
        openWeatherService = new OpenWeatherServiceImpl(openWeatherConnector, config);
    }

    @Test
    public void retrieveCurrentDataFromOpenWeatherTest() {

        CurrentWeatherDataDTO currentWeatherDataResponse = createCurrentWeatherDataDTO();
        FeignProxyClient feignProxyClient = mock(FeignProxyClient.class);

        when(config.getCurrentWeatherDataApiBaseUrl()).thenReturn("http://testurl/");
        when(config.getWeatherDataApiVersion()).thenReturn("2.5");
        when(config.getWeatherDataApiUnit()).thenReturn("metric");
        when(openWeatherConnector.connectTOCurrentDataProxyClient(any(), any())).thenReturn(feignProxyClient);
        when(feignProxyClient.execute()).thenReturn(currentWeatherDataResponse);

        CurrentWeatherDataDTO currentWeatherDataDTO = openWeatherService.retrieveCurrentDataFromOpenWeather("Berlin", "abdhdudn474djdjde343");

        assertEquals(Double.valueOf(12.7), currentWeatherDataDTO.getWeatherDataDTO().getTemp());
        assertEquals(Double.valueOf(1004.3), currentWeatherDataDTO.getWeatherDataDTO().getAirPressure());
        assertNotNull(currentWeatherDataDTO.getWeatherConditionDTOS());
        assertEquals(1, currentWeatherDataDTO.getWeatherConditionDTOS().size());
        assertEquals(1, currentWeatherDataDTO.getWeatherConditionDTOS().size());
        assertEquals("Thunderstorm", currentWeatherDataDTO.getWeatherConditionDTOS().get(0).getCodeName());

    }

    private CurrentWeatherDataDTO createCurrentWeatherDataDTO() {

        WeatherConditionDTO weatherConditionDTO = WeatherConditionDTO.builder()
                .code(210)
                .codeName("Thunderstorm")
                .build();
        List<WeatherConditionDTO> weatherConditionDTOList = new ArrayList<>();
        weatherConditionDTOList.add(weatherConditionDTO);

        WeatherDataDTO weatherDataDTO =WeatherDataDTO.builder()
                .temp(12.7)
                .airPressure(1004.3)
                .build();

        return CurrentWeatherDataDTO.builder()
                .weatherDataDTO(weatherDataDTO)
                .weatherConditionDTOS(weatherConditionDTOList)
                .build();
    }
}
