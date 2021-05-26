package com.tenera.interview.solution.weather.integration.test.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        glue = {"com.tenera.interview.solution.weather.integration.test.stepdefinitions"},
        plugin = {"pretty", "html:target/report.html"}, publish = true, monochrome = true, dryRun = false)
public class IntegrationTestRunner {

}
