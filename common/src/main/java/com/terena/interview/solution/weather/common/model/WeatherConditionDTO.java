package com.terena.interview.solution.weather.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherConditionDTO {

    @JsonProperty("id")
    private int code;

    @JsonProperty("main")
    private String codeName;

}
