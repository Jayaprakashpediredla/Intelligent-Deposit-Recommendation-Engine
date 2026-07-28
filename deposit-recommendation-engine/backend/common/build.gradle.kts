plugins {
    `java-library`
}

description = "Shared common library"

dependencies {

    implementation("org.springframework:spring-web:6.2.7")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Bean Validation API
    compileOnly("jakarta.validation:jakarta.validation-api:3.0.2")

    // Jackson annotations for shared DTOs
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:2.17.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.5.0")
    testImplementation("org.assertj:assertj-core:3.27.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.12.2")

    /*testImplementation(platform("org.junit:junit-bom:5.12.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.2")*/
}

tasks.test {
    useJUnitPlatform()
}