buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:3.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
    }


}

plugins {
    java
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
}
apply(plugin = "io.spring.dependency-management")


group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok:1.18.24")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.h2database:h2:2.1.214")
    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("com.lucadev:coinmarketcap-api:2.1")
    implementation("org.hibernate.orm:hibernate-core:6.1.0.Final")
    implementation("org.springframework.boot:org.springframework.boot.gradle.plugin:3.0.1")
}
tasks.withType<Test> {
    useJUnitPlatform()
}
