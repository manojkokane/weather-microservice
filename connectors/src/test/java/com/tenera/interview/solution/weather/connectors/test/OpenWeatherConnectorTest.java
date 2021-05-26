package com.tenera.interview.solution.weather.connectors.test;

import com.terena.interview.solution.weather.common.restclient.feign.FeignProxyClient;
import com.terena.interview.solution.weather.connectors.openwather.OpenWeatherConnector;
import com.terena.interview.solution.weather.connectors.openwather.client.CurrentDataProxyClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class OpenWeatherConnectorTest {

    private OpenWeatherConnector connector;

    @BeforeEach
    public void setUp() {
        connector = new OpenWeatherConnector();
    }

    @Test
    public void connectTOCurrentDataProxyClientTest() {

        FeignProxyClient feignProxyClient = connector.connectTOCurrentDataProxyClient("http://testurl/", mock(HashMap.class));

        assertNotNull(feignProxyClient);
        assertTrue(feignProxyClient instanceof CurrentDataProxyClient);
    }
}
