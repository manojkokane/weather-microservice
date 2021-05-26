package com.terena.interview.solution.weather.common.config;

import com.terena.interview.solution.weather.common.exception.ErrorMessageCodes;
import com.terena.interview.solution.weather.common.exception.WeatherServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * This class will act as an interceptor to feign error response mechanism
 * Custom Feign error decoder to throw meaningful error response back to client
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        log.error("An error occurred while consuming third party service", response.body().toString());
        switch (response.status()) {
            case 400:
                throw WeatherServiceException.builder()
                        .messageCode(ErrorMessageCodes.UPSTREAM_SERVER_INVALID_REQUEST)
                        .message(response.reason())
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            case 401:
                throw WeatherServiceException.builder()
                        .messageCode(ErrorMessageCodes.UPSTREAM_SERVER_UNAUTHORIZED)
                        .message(response.reason())
                        .status(HttpStatus.UNAUTHORIZED)
                        .build();
            case 404:
                throw WeatherServiceException.builder()
                        .messageCode(ErrorMessageCodes.UPSTREAM_SERVER_NOT_FOUND)
                        .message(response.reason())
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            default:
                throw WeatherServiceException.builder()
                        .messageCode(ErrorMessageCodes.UPSTREAM_SERVER_ERROR)
                        .message(response.reason())
                        .status(HttpStatus.valueOf(response.status()))
                        .build();
        }
    }
}
