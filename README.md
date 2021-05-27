# Terena Weather Micorservice

<!-- PROJECT LOGO -->
<p align="center">
  <a href="https://www.tenera.io/">
    <img src="https://assets.website-files.com/5f22f09cfb530b3558a588f2/60073ae974c5501c6268387a_Tenera%20Logo%202020%20Teal%20Gradient.svg" alt="Logo" width="80" height="80">
  </a>
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-weather-microservice">About Weather Microservice</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#setup-and-run">Setup and Run</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li>
        <a href="#approach">Approach</a>
        <ul>
            <li>
                <a href="#technical-implementation">Technical Implementation</a>
                <ul>
                    <li><a href="#retrieve-current-weather-data">Retrieve Current Weather Data</a></li>
                    <li><a href="#retrieve-historic-weather-data">Retrieve Historic Weather Data</a></li>
                    <li><a href="#build-and-push-docker-image">Build and Push Docker Image</a></li>
                </ul>
            </li>
            <li><a href="#ci-cd-and-multi-environment">CI CD and multi environment</a></li>
        </ul>
    </li>
    <li><a href="#further-improvements">Further Improvements</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About Weather Microservice
This is a potential implementation of weather microservice. Currently this microservice is responsible for the implementation of two major functionality:
* <a href="#retrieve-current-weather-data">Retrieve current weather data</a>
* <a href="#retrieve-historic-weather-data">Retrieve historic weather data</a>


### Built With

* Open API 3.0
* Spring Boot 2.5.0
* Spring Feign i.e. OpenFeign
* Spring Data JPA
* H2 Database
* Junit5 and Mockito for Unit Test
* Cucumber and RestAssured for Integration Test


<!-- GETTING STARTED -->
## Getting Started

Please go through the below steps to have your local copy up and running.

### Prerequisites

* Java 1.8
* Maven 3.x
* Docker - make sure you have docker hub account or any other image repository account
* OpenWeather service API Key - Since this microservice consumes open weather service, you will need a valid API key.
* Please make you have image repository server setting added in maven's settings.xml like below:
  ```sh
  <servers>
    <server>
        <id>docker.io</id>
        <username>add_your_username</username>
        <password>add_your_password</password>
    </server>
  </servers>
  ```

### Setup and Run

1. Clone the weather-microservice repository
   ```sh
   git clone git@github.com:manojkokane/weather-microservice.git
   ```
2. Navigate to the project directory
   ```sh
   cd weather-microservice
   ```
3. There are two way you can run the microservice
   * By running locally
     
       ```sh
       mvn clean install -Dheader.openweather.key=<open-weather-api-key>
       cd docker\docker-stage
       java -jar startup-1.0.0.jar
       ```
   * By running inside the docker container
        
      ```sh
      mvn clean install -DdeployDockerOnInstall -Ddocker.repo.user=<docker-user> -Dheader.openweather.key=<open-weather-api-key>
      docker run -p 9090:9090 <docker.repo.user>/weather-service:1.0.0
      ```
     Use `-DskipTests=true` to skip the step execution.<br/>
     Maven argument `-Dheader.openweather.key=<open-weather-api-key>` is specifically used by integration test



<!-- USAGE EXAMPLES -->
## Usage
Weather APIs are available at below mentioned URL
1. Current Weather API:
   ```sh
   HTTP Method: GET
   URL: http://localhost:9090/api/v1/weather/current?location=<city>
   
   Example:
   curl -X GET "http://localhost:9090/api/v1/weather/current?location=Berlin" -H  "accept: application/json" -H  "x-open-weather-api-key: <open_weather_api_key>"
   
   Note: It is mandatory to provide 'location' and header 'x-open-weather-api-key' with your own <open_weather_api_key>, otherwise microservice will throw a validation error.
   ```
2. Historic Weather API
   ```sh
   HTTP Method: GET
   URL: http://localhost:9090/api/v1/weather/history?location=<city>
   
   Example:
   curl -X GET "http://localhost:9090/api/v1/weather/history?location=Berlin,de" -H  "accept: application/json"
   
   Note: It is mandatory to provide 'location', otherwise microservice will throw a validation error.
   ```
Swagger UI is quite helpful while working with public APIs. This microservice also supports swagger UI and can be found at below URL:
```sh
http://localhost:9090/api/v1/swagger-ui.html
```

<!-- Approach -->
## Approach
* ### Technical Implementation
    * #### Retrieve Current Weather Data
     1. Client requests current weather data via `GET /weather/current` API by providing `city` name as a input.
     2. Since this microservice follows API first approach, it will validate the request before sending it over to further layers. It mainly validates request parameter i.e. `lcoation` and header `x-open-weather-api-key` 
     3. On Successful validation, WeatherController resolves such request and calls OpenWeatherService layer.
     4. OpenWeatherService obtains a connection to 3rd party weather data service via OpenWeatherConnector. OpenWeatherConnector simply creates a client for feign proxy.
     5. OpenWeatherService then executes feign proxy client request and receives current weather data.
     6. Interim weather data is then passed on to WeatherController, which then maps it to the intended response model and sends back to the client.
     7. in Parallel, WeatherController does send a **aync request** to HistoryWeatherService to save this data in H2 database for future reference e.g. while retrieving historic data.  
        
    * #### Retrieve Historic Weather Data
    1. Client requests historic weather data via `GET /weather/history` API by providing `city` name as a input.
    2. Since this microservice follows API first approach, it will validate the request before sending it over to further layers. It mainly validates request parameter i.e. `location`.
    3. WeatherController resolves such request and calls HistoryWeatherService layer.
    4. HistoryWeatherService, with the help of WeatherQueryResultRepository, retrieves recent query results we had got previously via `GET /wather/current` API.
    5. QueryResultRepository retrieves as many recent records as configured with `histoyCount` and provided input `city`.
    6. HistoryWeatherService also performs some business logic like calculating average temperature and average air pressure for all received historic records. 
    7. HistoryWeatherService sends interim result to WeatherController, which then maps it to intended response model and sends back to the client.
    
     * #### Build and Push Docker Image
     1. Build microservice using below command:
        ```sh
        mvn clean install -DdeployDockerOnInstall -Ddocker.repo.user=<docker-user> -Dheader.openweather.key=<open-weather-api-key>
        ```
     2. `startup` module will copy the executable jar to `doker/docker-stage` directory.
     3. `docker` module is solely responsible for building and pushing image to the repository server e.g. docker.io or nexus.
     4. `docker` module uses `io.spotify` plugin and `Dockerfile` to create an image and pushed it to the repository server.
    
* ### CI CD and multi environment
Currently microservice is well equipped to cope up with CI/CD tools like jenkins and multi environment support.
I have mostly worked with Jenkins and Helm (Kubernetes package manager) so I would be explaining here in that context.
I have placed `env.properties`, `version.properties` and helm structure `helm/weather-microservice/` in the root folder of the microservice. This can be used by Jenkins to automate the build and deploy process based on current sprint.
<br/>Usual process of Jenkins CI/CD would look like below:<br>
1. Have the build server in place.
2. Clone the microservice repo on build server.
3. Based on env.properties and version.properties, make changes to the pom versions, and docker image version.
4. Build the microservice using `mvn clean install -DdeployDockerOnInstall -Ddocker.repo.user=<docker-user>`.
5. Push the local image to the your image repository e.g. docker hub or nexus etc.
6. Modify helm artifacts, mostly `deployment.yaml` and `values.yaml`, with correct image version name and tag.
7. Create a helm chart out of updated artifacts and push the helm chart to your chart museum i.e.chart server.
8. Commit the poms by increasing version by 1 so that earlier image and chart can not be overridden by subsequent CI/CD process.
9. This chart now can be installed on any kubernetes cluster by providing env specific override `values.yaml`.
10. Microservice also supports spring based profiling, so if you override `springprofile` value, it will picks up that profile while installing helm chart. Currently it supports `dev`, `qa`, `stage`, and `prod` profiles.

<!-- Improvements -->
## Further Improvements
1. Message Codes and Model Mappers can generated using Open API codegen mechanism.
2. Integration test can be separated and ran independently.
3. Discovery pattern can be used to register our microservice to the discovery service e.g. Netflix Eureka
4. If we go for discovery pattern then having API Gateway to manage request routing, load balancing, authentication, caching would be good practice.
5. Circuit breaker pattern e.g. Hystrix can be used to handle fallback mechanism in case of failure in inter microservice communication.
6. Event-driven architecture helps to manage most of the circuit breaker pattern scenarios efficiently.
7. Having separate Config server is also good practice instead of using embedded application.properties/yaml 
8. Distributed logging helps in case of distributed microservice architecture, e.g. ELK.

<!-- CONTACT -->
## Contact

Manoj Kokane - [@LinedIn](https://www.linkedin.com/in/manoj-kokane-chincholi) - manoj.kokane@hotmail.com

Project Link: [https://github.com/manojkokane/weather-microservice](https://github.com/manojkokane/weather-microservice)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/github_username
