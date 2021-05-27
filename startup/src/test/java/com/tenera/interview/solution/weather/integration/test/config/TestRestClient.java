package com.tenera.interview.solution.weather.integration.test.config;

import com.tenera.interview.solution.weather.integration.test.config.TestRestConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Getter
public class TestRestClient {

	@Autowired
	private TestRestConfig restConfig;

	public void initForEndpoint() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("x-open-weather-api-key", restConfig.getHeaderOpenWeatherKey());

		//@formatter:off
		RestAssured.requestSpecification = new RequestSpecBuilder()
				.addHeaders(headers)
				.setBaseUri(restConfig.getBaseUri())
				.setPort(Integer.valueOf(restConfig.getPort()))
				.setBasePath(restConfig.getBasePath())
				.build();
		//@formatter:on
	}
}
