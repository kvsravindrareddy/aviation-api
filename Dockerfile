#!/bin/bash
FROM openjdk:11
ARG JAR_FILE=target/*.jar
#RUN mkdir -p /opt/aviation-api
#RUN chown 777 /opt/aviation-api/
WORKDIR /opt/aviation-api
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]