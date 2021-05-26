package com.tenera.interview.solution.weather.integration.test.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Setter
@Getter
@PropertySource("classpath:test.properties")
public class TestRestConfig {

	@Value("${test.base.uri}")
	private String baseUri;

	@Value("${test.port}")
	private String port;

	@Value("${test.base.path}")
	private String basePath;

	@Value("${test.header.openweather.key}")
	private String headerOpenWeatherKey;

	//endPoints
	@Value("${weather.get.current.data.rest.endpoint}")
	private String weatherGetCurrentDataRestEndpoint;

	@Value("${weather.get.historic.data.rest.endpoint}")
	private String weatherGetHistoricDataRestEndpoint;

}
