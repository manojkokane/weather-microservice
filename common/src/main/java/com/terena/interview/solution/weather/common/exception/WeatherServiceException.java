package com.terena.interview.solution.weather.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class WeatherServiceException extends RuntimeException {
    private ErrorMessageCodes messageCode;
    private String message;
    private Throwable throwable;
    private HttpStatus status;
}
