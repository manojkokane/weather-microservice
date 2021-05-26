FROM openjdk:8-jdk-alpine
ARG JAR_FILE=docker-stage/*.jar
COPY ${JAR_FILE} weather-service.jar
ENTRYPOINT ["java","-jar","/weather-service.jar"]