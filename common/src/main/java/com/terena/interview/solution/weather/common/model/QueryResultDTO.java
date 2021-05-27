package com.terena.interview.solution.weather.common.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Model/dto class to store query results data received from database
 */
@Builder
@Getter
public class QueryResultDTO {
    private String queryCity;
    private Double responseTemp;
    private Double responsePressure;
    private Boolean responseUmbrella;
}
