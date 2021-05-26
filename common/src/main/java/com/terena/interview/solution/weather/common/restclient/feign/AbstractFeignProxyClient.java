package com.terena.interview.solution.weather.common.restclient.feign;

import com.terena.interview.solution.weather.common.config.FeignErrorDecoder;
import com.terena.interview.solution.weather.common.config.FeignRetryer;
import com.terena.interview.solution.weather.common.constant.WeatherServiceConstant;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Abstract Feign client.
 * This is generic client which is responsible to create a feign client based on target proxy class provided.
 * @param <T> - a target proxy.
 */
@Setter
@Getter
public abstract class AbstractFeignProxyClient<T> implements FeignProxyClient {

    private String clientUrl;
    private Class<T> clientClass;
    private Map<String, Collection<String>> requestQueries;

    /**
     * A method to build feign rest client based on target class, target url, target proxy class provided.
     * @return @{@link FeignProxyClient}
     */
    @Override
    public T build() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .retryer(new FeignRetryer())
                .errorDecoder(new FeignErrorDecoder())
                .requestInterceptor(buildRequestInterceptor())
                .target(getClientClass(), getClientUrl());
    }

    private RequestInterceptor buildRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header(WeatherServiceConstant.HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            requestTemplate.header(WeatherServiceConstant.HEADER_ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            Map<String, Collection<String>> queries = getNonExistingQueries(requestTemplate, getRequestQueries());
            requestTemplate.queries(queries);
        };
    }

    /**
     * add request parameters if those are not already present. This is an edge case for retry handling
     * @param requestTemplate
     * @param requestQueries
     * @return
     */
    protected Map<String, Collection<String>> getNonExistingQueries(RequestTemplate requestTemplate, Map<String, Collection<String>> requestQueries) {
        return requestQueries.entrySet().stream()
                .filter(entry -> !requestTemplate.queries().containsKey(entry.getKey()))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }
}
