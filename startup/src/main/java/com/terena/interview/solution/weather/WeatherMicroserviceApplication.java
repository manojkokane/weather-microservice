package com.terena.interview.solution.weather;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeatherMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherMicroserviceApplication.class, args);
	}

	@Value("${app.info.title}")
	private String title;
	@Value("${app.info.description}")
	private String description;
	@Value("${app.info.version}")
	private String appVersion;
	@Value("${app.info.license}")
	private String license;
	@Value("${app.info.contact}")
	private String contact;

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
				.info(new Info()
				.title(title)
				.version(appVersion)
				.description(description)
				.license(new License().name(license).url(contact)));
	}
}
