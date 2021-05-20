FROM gradle:6.7.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim
WORKDIR /var/app/royhome-api
COPY . .
ENTRYPOINT ["java","-jar","build/libs/royhome-api-0.0.1-SNAPSHOT.jar"]
