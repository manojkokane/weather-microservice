package com.terena.interview.solution.weather.common.restclient.feign;

/**
 * Feign client interface.
 * This provided abstract methods to interact with a feign client, which is based on target proxy class provided.
 * @param <T>
 */
public interface FeignProxyClient<T> {

    T build();
    <R> R execute();
}

