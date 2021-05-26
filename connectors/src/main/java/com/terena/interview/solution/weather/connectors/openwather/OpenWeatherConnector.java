package com.terena.interview.solution.weather.connectors.openwather;

import com.terena.interview.solution.weather.common.restclient.feign.AbstractFeignProxyClient;
import com.terena.interview.solution.weather.common.restclient.feign.FeignProxyClient;
import com.terena.interview.solution.weather.connectors.openwather.client.CurrentDataProxyClient;
import com.terena.interview.solution.weather.connectors.openwather.proxy.WeatherDataProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * Connector class to setup connection with intended target proxy
 */
@Slf4j
@Component
public class OpenWeatherConnector {

    /**
     * Sets up connection properties to connect to current data proxy of 3rd party OpenWeather service
     * return rest client instance for intended target proxy
     * @param clientUrl
     * @param requestQueries
     * @return @{@link FeignProxyClient}
     */
    public FeignProxyClient connectTOCurrentDataProxyClient(String clientUrl, Map<String, Collection<String>> requestQueries) {
        log.info(String.format("Setting up connection properties to connect to OpenWeather's current data proxy client"));
        AbstractFeignProxyClient dataProxyClient = new CurrentDataProxyClient();

        dataProxyClient.setRequestQueries(requestQueries);
        dataProxyClient.setClientClass(WeatherDataProxy.class);
        dataProxyClient.setClientUrl(clientUrl);

        log.info(String.format("Connection properties has set successfully to connect to  OpenWeather's current data proxy client"));
        return  dataProxyClient;
    }
}
