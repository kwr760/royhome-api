import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "net.roy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

plugins {
  id("org.springframework.boot") version "3.0.2"
  id("org.flywaydb.flyway") version "9.14.1"
  id("io.spring.dependency-management") version "1.1.0"
  id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
  id("io.gitlab.arturbosch.detekt") version "1.22.0"
  id("jacoco")
  kotlin("jvm") version "1.8.10"
  kotlin("plugin.spring") version "1.8.10"
  kotlin("plugin.jpa") version "1.8.10"
  kotlin("plugin.serialization") version "1.8.10"
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
  google()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-data-rest")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-websocket")

  implementation("org.springframework.plugin:spring-plugin-core")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.flywaydb:flyway-core")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation("io.springfox:springfox-boot-starter:3.0.0")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
  implementation("com.google.code.gson:gson:2.10.1")

  compileOnly("org.projectlombok:lombok")

  runtimeOnly("org.postgresql:postgresql")
  annotationProcessor("org.projectlombok:lombok")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.mockk:mockk:1.13.4")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = JavaVersion.VERSION_17.toString()
  }
}
tasks.withType<Test> {
  useJUnitPlatform()
}
tasks.register("bootRunDev") {
  group = "application"
  description = "Runs the Spring Boot application with the dev profile"
  doFirst {
    tasks.bootRun.configure {
      systemProperty("spring.profiles.active", "dev")
    }
  }
  finalizedBy("bootRun")
}
tasks.bootRun {
  systemProperties(System.getProperties().toMap() as Map<String, Object>)
}
tasks.test {
  finalizedBy(tasks.jacocoTestReport)
}
tasks.check {
  finalizedBy(
    tasks.jacocoTestReport,
    tasks.ktlintCheck
  )
}
tasks.jacocoTestReport {
  finalizedBy(tasks.jacocoTestCoverageVerification)
  dependsOn(tasks.test) // tests are required to run before generating the report
  reports {
    xml.required.set(true)
    csv.required.set(false)
    html.outputLocation.set(file("$buildDir/reports/coverage"))
  }
}

val excludeList = listOf(
  "net.royhome.Application*",
  "net.royhome.*.config.*",
  "net.royhome.*.constant.*",
  "net.royhome.*.model.*",
  "net.royhome.*.repository.*",
  "net.royhome.resume.model.*",
  "net.royhome.resume.repository.*",
  "net.royhome.tictactoe.service.MessagingService"
)
tasks.jacocoTestCoverageVerification {
  violationRules {
    rule {
      element = "CLASS"
      excludes = excludeList
      limit {
        minimum = "1.0".toBigDecimal()
      }
    }
    rule {
      element = "CLASS"
      includes = listOf("net.royhome.tictactoe.service.MessagingService")
      limit {
        minimum = "0.9".toBigDecimal()
      }
    }
  }
}
jacoco {
  toolVersion = "0.8.8"
}
