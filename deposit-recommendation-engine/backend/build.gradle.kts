// Root build for backend microservices multi-module project
subprojects {
    apply(plugin = "java")

    // Java toolchain configured from gradle.properties (javaVersion)
    afterEvaluate {
        configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(findProperty("javaVersion")?.toString()?.toInt() ?: 21))
            }
        }
    }

    // Common encoding and compilation options
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    // Common test dependencies and configuration
    dependencies {
        add("testImplementation", "org.junit.jupiter:junit-jupiter-api:5.9.2")
        add("testRuntimeOnly", "org.junit.jupiter:junit-jupiter-engine:5.9.2")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

