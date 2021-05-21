FROM gradle:6.7.1-jdk11 AS build
COPY --chown=gradle:gradle . /var/gradle/src
WORKDIR /var/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim
COPY --from=build /var/gradle/src/build/libs/*.jar /var/app/royhome-api.jar
ENTRYPOINT ["java","-jar","/var/app/royhome-api.jar"]
