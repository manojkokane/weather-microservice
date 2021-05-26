package com.tenera.interview.solution.weather.integration.test.stepdefinitions;

import com.tenera.interview.solution.weather.integration.test.TestRestClient;
import com.tenera.interview.solution.weather.integration.test.config.TestRestConfig;
import com.terena.interview.solution.weather.WeatherMicroserviceApplication;
import com.terena.interview.solution.weather.models.WeatherHistoryResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {WeatherMicroserviceApplication.class, TestRestClient.class, TestRestConfig.class})
public class WeatherServiceStepDefinitions {

    @Autowired
	private TestRestClient testRestClient;

    Response response;
    Response interimResponse;

    @Given("^ReST Client is setup$")
    public void rest_Client_is_setup() throws Throwable {

        testRestClient.initForEndpoint();
        response = null;
    }
    
    @When("^a request is made to get current weather data with location as \"([^\"]*)\"$")
    public void a_request_is_made_to_get_current_weather_data_with_location_as(String location) throws Throwable {

        //@formatter:off
        RequestSpecification request = given().with().queryParam("location", location);
        response = request.when().get(testRestClient.getRestConfig().getWeatherGetCurrentDataRestEndpoint());
    }

    @Then("^the current weather data for location \"([^\"]*)\" is retrieved successfully$")
    public void the_current_weather_data_for_location_is_retrieved_successfully(String location) throws Throwable {

        assertEquals(200, response.statusCode());
        assertNotNull(response);
    }

    @When("^a request is made to get historic weather data for location \"([^\"]*)\"$")
    public void a_request_is_made_to_get_historic_weather_data_for_location(String location) throws Throwable {

        //@formatter:off
        RequestSpecification request = given().with().queryParam("location", location);
        response = request.when().get(testRestClient.getRestConfig().getWeatherGetHistoricDataRestEndpoint());
    }

    @Then("^the historic weather data for location \"([^\"]*)\" is retrieved successfully$")
    public void the_historic_weather_data_for_location_is_retrieved_successfully(String location) throws Throwable {

        assertEquals(200, response.statusCode());
        assertNotNull(response);

        WeatherHistoryResponse historyResponse = response.as(WeatherHistoryResponse.class);
        assertNotNull(historyResponse.getHistory());
        assertEquals(1, historyResponse.getHistory().size());
    }
}
