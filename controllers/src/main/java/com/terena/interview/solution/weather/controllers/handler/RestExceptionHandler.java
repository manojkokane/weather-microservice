package com.terena.interview.solution.weather.controllers.handler;

import com.terena.interview.solution.weather.common.exception.ErrorMessageCodes;
import com.terena.interview.solution.weather.common.exception.WeatherServiceException;
import com.terena.interview.solution.weather.controllers.util.ExceptionHandlerUtil;
import com.terena.interview.solution.weather.models.ErrorResponse;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingHeaderException(Exception exception, HttpServletRequest request) {
        log.error(ErrorMessageCodes.MISSING_HEADER.getMessage(), exception);
        ErrorResponse errorResponse = ExceptionHandlerUtil.createErrorResponseForCode(ErrorMessageCodes.MISSING_HEADER, exception, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        log.error(ErrorMessageCodes.INVALID_REQUEST.getMessage(), exception);
        ErrorResponse errorResponse = ExceptionHandlerUtil.createErrorResponseForCode(ErrorMessageCodes.INVALID_REQUEST, exception, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        log.error(ErrorMessageCodes.INVALID_REQUEST.getMessage(), exception);
        ErrorResponse errorResponse = ExceptionHandlerUtil.createErrorResponseForCode(ErrorMessageCodes.INVALID_REQUEST, exception, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ErrorResponse> handleRetryableException(RetryableException exception, HttpServletRequest request) {
        log.error(ErrorMessageCodes.REQUEST_TIMEOUT.getMessage(), exception);
        ErrorResponse errorResponse = ExceptionHandlerUtil.createErrorResponseForCode(ErrorMessageCodes.REQUEST_TIMEOUT, exception, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception, HttpServletRequest request) {
        log.error(ErrorMessageCodes.INVALID_REQUEST.getMessage(), exception);
        ErrorResponse errorResponse = ExceptionHandlerUtil.createErrorResponseForCode(ErrorMessageCodes.MISSING_INPUT, exception, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<ErrorResponse> handleWeatherServiceException(WeatherServiceException exception, HttpServletRequest request) {
        log.error(exception.getMessageCode().getMessage(), exception);
        ErrorResponse errorResponse = ExceptionHandlerUtil.createErrorResponseForCode(exception.getMessageCode(), exception, request);
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleWeatherServiceException(Exception exception, HttpServletRequest request) {
        log.error(ErrorMessageCodes.UNDEFINED_ERROR.getMessage(), exception);
        ErrorResponse errorResponse = ExceptionHandlerUtil.createErrorResponseForCode(ErrorMessageCodes.UNDEFINED_ERROR, exception, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
