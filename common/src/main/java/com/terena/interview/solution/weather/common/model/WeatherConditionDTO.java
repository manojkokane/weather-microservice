package com.terena.interview.solution.weather.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherConditionDTO {

    @JsonProperty("id")
    private int code;

    @JsonProperty("main")
    private String codeName;

}
