package com.tenera.interview.solution.weather.integration.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        plugin = {"pretty", "html:target/report.html"}, publish = false, monochrome = true, dryRun = false)
public class TestIntegrationRunner {
}
