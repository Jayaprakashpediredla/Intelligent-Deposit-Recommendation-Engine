plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val jjwtVersion: String by project
val lombokVersion: String by project

dependencies {

    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // Servlet API for filter compilation
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

// Security module is a library; disable bootJar to avoid requiring a main class
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
