package com.terena.interview.solution.weather.common.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Entity class to store weather query results
 */
@Entity
@Table(name = "weather_query")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeatherQueryResult {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "city")
    @NotNull
    @NotEmpty
    private String queryCity;

    @Column(name = "temp")
    @NotNull
    private Double responseTemp;

    @Column(name = "pressure")
    @NotNull
    private Double responsePressure;

    @Column(name = "umbrella")
    @NotNull
    private Boolean responseUmbrella;

    @Column(name = "created_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime createdDate;

}
