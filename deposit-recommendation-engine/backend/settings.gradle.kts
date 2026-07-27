pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        id("org.springframework.boot") version "3.5.4"
        id("io.spring.dependency-management") version "1.1.7"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        mavenCentral()
    }
}

rootProject.name = "intelligent-deposit-recommendation-engine"

include(
    "common",
    "security",
    "gateway",
    "customer-service",
    "transaction-service",
    "recommendation-service",
    "notification-service"
)