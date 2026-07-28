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
    add("implementation", "org.springframework.cloud:spring-cloud-starter-gateway:${findProperty("springCloudVersion") ?: "4.1.0"}")
    add("implementation", "org.springframework.boot:spring-boot-starter-security")

    add("implementation", project(":security"))

    add("developmentOnly", "org.springframework.boot:spring-boot-devtools")

    add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("com.nexuswealth.gateway.GatewayApplication")
}

