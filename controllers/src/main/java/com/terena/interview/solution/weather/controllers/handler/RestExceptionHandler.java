package com.terena.interview.solution.weather.controllers.handler;

import com.terena.interview.solution.weather.common.exception.ErrorMessageCodes;
import com.terena.interview.solution.weather.controllers.util.ExceptionHandlerUtil;
import com.terena.interview.solution.weather.models.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleWeatherServiceException(Exception exception, HttpServletRequest request) {
        log.error(ErrorMessageCodes.UNDEFINED_ERROR.getMessage(), exception);
        ErrorResponse errorResponse = ExceptionHandlerUtil.createErrorResponseForCode(ErrorMessageCodes.UNDEFINED_ERROR, exception, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
