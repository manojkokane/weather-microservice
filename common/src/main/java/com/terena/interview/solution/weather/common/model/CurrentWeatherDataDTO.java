package com.terena.interview.solution.weather.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherDataDTO {

    @JsonProperty("weather")
    private List<WeatherConditionDTO> weatherConditionDTOS;

    @JsonProperty("main")
    private WeatherDataDTO weatherDataDTO;

}
