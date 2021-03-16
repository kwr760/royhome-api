import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.4.2"
  id("org.flywaydb.flyway") version "7.5.4"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
  id("io.gitlab.arturbosch.detekt") version "1.16.0"
  kotlin("jvm") version "1.4.21"
  kotlin("plugin.spring") version "1.4.21"
  kotlin("plugin.jpa") version "1.4.21"
}

allOpen {
  annotation("javax.persistence.Entity")
  annotation("javax.persistence.MappedSuperclass")
  annotation("javax.persistence.Embeddable")
}

group = "net.roy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

// apply from: "$rootDir/jacoco.gradle.kts"

repositories {
  mavenCentral()
  jcenter()
  maven { url = uri("https://repo.spring.io/milestone") }
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

//  implementation("io.springfox:springfox-swagger2:3.0.0")
//  implementation("io.springfox:springfox-swagger-ui:3.0.0")
//  implementation("io.springfox:springfox-boot-starter:3.0.0")
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

flyway {
  url = "jdbc:postgresql://localhost:5432/newhome"
  user = "postgres"
  password = "v7starap"
}
