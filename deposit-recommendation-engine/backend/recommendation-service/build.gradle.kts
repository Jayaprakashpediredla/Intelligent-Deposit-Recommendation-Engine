buildscript {
    repositories { mavenCentral() }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${findProperty("springBootVersion") ?: "3.5.0"}")
        classpath("io.spring.gradle:dependency-management-plugin:${findProperty("springDependencyManagementVersion") ?: "1.1.7"}")
    }
}

apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")

dependencies {
    add("implementation", "org.springframework.boot:spring-boot-starter-web")
    add("implementation", "org.springframework.boot:spring-boot-starter-data-jpa")
    add("implementation", "org.postgresql:postgresql:${findProperty("postgresVersion") ?: "42.7.7"}")

    add("implementation", "org.apache.commons:commons-math3:3.6.1")
    add("implementation", "org.apache.commons:commons-csv:1.10.0")

    add("implementation", project(":common"))
    add("implementation", project(":security"))

    add("developmentOnly", "org.springframework.boot:spring-boot-devtools")

    add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("com.nexuswealth.recommendation.RecommendationServiceApplication")
}

