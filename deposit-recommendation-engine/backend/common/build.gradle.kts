plugins {
    `java-library`
}

description = "Shared common library"

dependencies {

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Bean Validation API
    compileOnly("jakarta.validation:jakarta.validation-api:3.0.2")

    // Jackson annotations for shared DTOs
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:2.17.0")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")

}