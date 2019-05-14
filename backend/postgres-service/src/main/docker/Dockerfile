FROM openjdk:11.0.2-jdk-slim-stretch
MAINTAINER Andrey Romanov <steampart@gmail.com>

WORKDIR /usr/src/application

ARG PORT

EXPOSE ${PORT}

ARG JAR_FILE
ARG SPRING_PROFILES_ACTIVE
ARG JVM_OPTS

ADD ${JAR_FILE} app.jar

CMD java ${JVM_OPTS} -jar /app.jar