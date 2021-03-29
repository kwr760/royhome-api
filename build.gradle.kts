import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "net.roy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
  id("org.springframework.boot") version "2.4.2"
  id("org.flywaydb.flyway") version "7.5.4"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
  id("io.gitlab.arturbosch.detekt") version "1.16.0"
  id("jacoco")
  kotlin("jvm") version "1.4.21"
  kotlin("plugin.spring") version "1.4.21"
  kotlin("plugin.jpa") version "1.4.21"
}

allOpen {
  annotation("javax.persistence.Entity")
  annotation("javax.persistence.MappedSuperclass")
  annotation("javax.persistence.Embeddable")
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-data-rest")
  implementation("org.springframework.boot:spring-boot-starter-web")

  implementation("org.springframework.plugin:spring-plugin-core")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.flywaydb:flyway-core")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation("io.springfox:springfox-boot-starter:3.0.0")

  compileOnly("org.projectlombok:lombok")

  developmentOnly("org.springframework.boot:spring-boot-devtools")

  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.postgresql:postgresql")
  annotationProcessor("org.projectlombok:lombok")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}
tasks.withType<Test> {
  useJUnitPlatform()
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
    xml.isEnabled = true
    csv.isEnabled = false
    html.destination = file("$buildDir/reports/coverage")
  }
}

val excludeList = listOf(
  "net.royhome.api.ApplicationKt",
  "net.royhome.api.config.*",
  "net.royhome.api.model.*",
  "net.royhome.api.repository.*"
)
tasks.jacocoTestCoverageVerification {
  violationRules {
    rule {
      element = "CLASS"
      excludes = excludeList
      limit {
        counter = "INSTRUCTION"
        minimum = "1.0".toBigDecimal()
      }
    }
    rule {
      element = "CLASS"
      excludes = excludeList
      limit {
        counter = "BRANCH"
        value = "COVEREDRATIO"
        minimum = "1.0".toBigDecimal()
      }
    }
    rule {
      element = "CLASS"
      excludes = excludeList
      limit {
        counter = "COMPLEXITY"
        value = "COVEREDRATIO"
        minimum = "1.0".toBigDecimal()
      }
    }
  }
}
jacoco {
  toolVersion = "0.8.6"
}
flyway {
  url = "jdbc:postgresql://localhost:5432/newhome"
  user = "postgres"
  password = "v7starap"
}
