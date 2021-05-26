package com.terena.interview.solution.weather.common.exception;

import lombok.Getter;

/**
 * Error Message Codes
 * These are potential error codes that are expected to be thrown by microservice for known failures.
 */
@Getter
public enum ErrorMessageCodes {

    UPSTREAM_SERVER_UNAUTHORIZED("UPSTREAM_SERVER_UNAUTHORIZED", "An authorization error occurred while processing third party service request", "Please check authentication details like key or token is correct"),
    UPSTREAM_SERVER_INVALID_REQUEST("UPSTREAM_SERVER_INVALID_REQUEST", "An error occurred while validating third party service request", "Please check input to third party service is valid"),
    UPSTREAM_SERVER_NOT_FOUND("UPSTREAM_SERVER_NOT_FOUND", "Third party service don't find the resource you are looking for", "Please change input to third party service and try again"),
    UPSTREAM_SERVER_ERROR("UPSTREAM_SERVER_ERROR", "An error occurred while processing third party service request", "Please application logs for more details");

    ErrorMessageCodes(String errorCode, String message, String suggestion) {
        this.errorCode = errorCode;
        this.message = message;
        this.suggestion = suggestion;
    }

    private String errorCode;
    private String message;
    private String suggestion;
}
