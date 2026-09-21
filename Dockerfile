# Stage 1
FROM gradle:8.8-jdk21 AS build

WORKDIR /app

COPY . .

RUN chmod +x gradlew && ./gradlew clean bootJar --no-daemon

# Stage 2
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.ja