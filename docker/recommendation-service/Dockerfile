# Build stage
FROM eclipse-temurin:21-jdk as builder

WORKDIR /app

# Copy gradle wrapper and build files
COPY ../../deposit-recommendation-engine/backend/gradlew .
COPY ../../deposit-recommendation-engine/backend/gradle ./gradle
COPY ../../deposit-recommendation-engine/backend/gradle.properties .
COPY ../../deposit-recommendation-engine/backend/settings.gradle.kts .
COPY ../../deposit-recommendation-engine/backend/build.gradle.kts .

# Copy recommendation-service module
COPY ../../deposit-recommendation-engine/backend/recommendation-service ./recommendation-service
COPY ../../deposit-recommendation-engine/backend/common ./common
COPY ../../deposit-recommendation-engine/backend/security ./security

# Build
RUN chmod +x gradlew && ./gradlew :recommendation-service:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/recommendation-service/build/libs/recommendation-service-*.jar app.jar

EXPOSE 8003

ENTRYPOINT ["java", "-jar", "app.jar"]

