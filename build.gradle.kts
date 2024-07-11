plugins {
	java
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "com.lakshaygpt28"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.2.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.redisson:redisson-spring-boot-starter:3.16.3")
	implementation("org.postgresql:postgresql")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation ("org.flywaydb:flyway-core:9.21.2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
