package com.terena.interview.solution.weather.controllers.util;

import com.terena.interview.solution.weather.common.exception.ErrorMessageCodes;
import com.terena.interview.solution.weather.models.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;

public class ExceptionHandlerUtil {

    public static ErrorResponse createErrorResponseForCode(ErrorMessageCodes errorCode, Throwable throwable, HttpServletRequest request) {
        return new ErrorResponse().code(errorCode.getErrorCode())
                .message(errorCode.getMessage())
                .reason(ExceptionUtils.getRootCauseMessage(throwable))
                .suggestion(errorCode.getSuggestion())
                .resource(request.getRequestURI());
    }
}
