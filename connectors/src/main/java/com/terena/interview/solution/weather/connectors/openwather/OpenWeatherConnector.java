package com.terena.interview.solution.weather.connectors.openwather;

import com.terena.interview.solution.weather.common.restclient.feign.AbstractFeignProxyClient;
import com.terena.interview.solution.weather.common.restclient.feign.FeignProxyClient;
import com.terena.interview.solution.weather.connectors.openwather.client.CurrentDataProxyClient;
import com.terena.interview.solution.weather.connectors.openwather.proxy.WeatherDataProxy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
public class OpenWeatherConnector {

    @PostConstruct
    public void init() {

    }

    @SneakyThrows
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
