plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("org.jetbrains.intellij") version "1.13.0"
    id("application")
    id("jacoco")
}

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases")
}

group = "com.reason"
version = properties["plugin_version"]!!

intellij {
    version.set("2022.2.5")
    type.set("IC") // Target IDE Platform
    plugins.set(listOf("com.intellij.java"))
}

dependencies {
// https://mvnrepository.com/artifact/com.jetbrains.intellij.idea/ideaIC
    implementation("com.jetbrains.intellij.idea:ideaIC:2022.2.5")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks {
    buildSearchableOptions {
        enabled = false
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}