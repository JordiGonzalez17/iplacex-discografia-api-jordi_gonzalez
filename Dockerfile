# Stage 1
FROM gradle:8.8-jdk21 AS build

WORKDIR /app

COPY . .

RUN ./gradlew clean bootJar --no-daemon

# Stage 2
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.ja