package com.tenera.interview.solution.weather.integration.test.stepdefinitions;

import com.tenera.interview.solution.weather.integration.test.config.TestRestClient;
import com.tenera.interview.solution.weather.integration.test.config.TestRestConfig;
import com.terena.interview.solution.weather.WeatherMicroserviceApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {WeatherMicroserviceApplication.class, TestRestClient.class, TestRestConfig.class})
public class AbstractStepDefinitions {

    @Autowired
    protected TestRestClient testRestClient;

    protected Response response;
}
