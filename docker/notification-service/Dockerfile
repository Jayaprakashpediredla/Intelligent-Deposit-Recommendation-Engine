# Build stage
FROM eclipse-temurin:21-jdk as builder

WORKDIR /app

# Copy gradle wrapper and build files
COPY ../../deposit-recommendation-engine/backend/gradlew .
COPY ../../deposit-recommendation-engine/backend/gradle ./gradle
COPY ../../deposit-recommendation-engine/backend/gradle.properties .
COPY ../../deposit-recommendation-engine/backend/settings.gradle.kts .
COPY ../../deposit-recommendation-engine/backend/build.gradle.kts .

# Copy notification-service module
COPY ../../deposit-recommendation-engine/backend/notification-service ./notification-service
COPY ../../deposit-recommendation-engine/backend/common ./common
COPY ../../deposit-recommendation-engine/backend/security ./security

# Build
RUN chmod +x gradlew && ./gradlew :notification-service:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/notification-service/build/libs/notification-service-*.jar app.jar

EXPOSE 8004

ENTRYPOINT ["java", "-jar", "app.jar"]

