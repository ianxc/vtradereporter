plugins {
    java
    jacoco
    id("io.freefair.lombok") version "8.12.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "3.4.2"
}

group = "com.ianxc"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

@Suppress("UnstableApiUsage")
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("org.flywaydb:flyway-core")
    implementation(libs.mapstruct)
    implementation(libs.runtime.javadoc)
    implementation(libs.springdoc.openapi)

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor(libs.hibernate.jpamodelgen)
    annotationProcessor(libs.mapstruct.processor)
    annotationProcessor(libs.runtime.javadoc.scribe)

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}
