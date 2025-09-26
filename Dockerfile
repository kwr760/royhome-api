## Multi-stage Dockerfile for royhome-api

# Builder stage
FROM gradle:8-jdk17 AS builder
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . /home/gradle/project
RUN gradle --no-daemon clean bootJar

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /home/gradle/project/build/libs/*.jar /app/app.jar

# Healthcheck - adjust endpoint if actuator is configured differently
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/app/app.jar"]
FROM gradle:6.7.1-jdk11 AS build
COPY --chown=gradle:gradle . /var/gradle/src
WORKDIR /var/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim
COPY --from=build /var/gradle/src/build/libs/*.jar /var/app/royhome-api.jar
ENTRYPOINT ["java","-jar","/var/app/royhome-api.jar"]
