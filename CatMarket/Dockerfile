FROM gradle:9.1.0-jdk21-alpine AS build

WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY src src

RUN ./gradlew clean build -x test

FROM gcr.io/distroless/java21-debian12

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
