plugins {
    kotlin("jvm") version "2.2.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files("run/Server/HytaleServer.jar"))
}

tasks.test {
    useJUnitPlatform()
}