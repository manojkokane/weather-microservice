package com.terena.interview.solution.weather.connectors.openwather.client;

import com.terena.interview.solution.weather.common.restclient.feign.AbstractFeignProxyClient;
import com.terena.interview.solution.weather.connectors.openwather.proxy.WeatherDataProxy;
import lombok.NoArgsConstructor;

/**
 * Current Data Proxy Client
 * This builds proxy client for current data and retrieves result
 */
@NoArgsConstructor
public class CurrentDataProxyClient extends AbstractFeignProxyClient {

    @Override
    public Object execute() {
        WeatherDataProxy weatherDataProxy = (WeatherDataProxy) build();
        return weatherDataProxy.retrieveCurrentWeatherData();
    }
}
